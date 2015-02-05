/**
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at
  
      http://www.apache.org/licenses/LICENSE-2.0
  
    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.    
**/

package com.github.venkateshamurthy.designpatterns.builders;

import static com.github.venkateshamurthy.designpatterns.builders.FluentBuilders.CMDOPTIONS.CLASS_NAMES;
import static com.github.venkateshamurthy.designpatterns.builders.FluentBuilders.CMDOPTIONS.FILE_LISTING;
import static com.github.venkateshamurthy.designpatterns.builders.FluentBuilders.CMDOPTIONS.HELP;
import static com.github.venkateshamurthy.designpatterns.builders.FluentBuilders.CMDOPTIONS.SET_METHOD_PATTERN;
import static com.github.venkateshamurthy.designpatterns.builders.FluentBuilders.CMDOPTIONS.SRC_FOLDER;

import java.beans.PropertyDescriptor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.fuin.srcgen4javassist.SgClass;
import org.fuin.srcgen4javassist.SgClassPool;

import com.fluentinterface.ReflectionBuilder;
import com.fluentinterface.builder.Builder;

/** A Simple utility class to create Fluent Builders for a reasonably complex objects(POJOs) that are usually auto-generated with void returning set
 * methods. These Builders could come in handy when the number of properties in a POJO quickly run out of hand (say >4).
 * <p>
 * It basically creates an interface by capturing and adding the typical setter methods of an object (typically POJO) with an intent to proxy-wire the
 * builder interface to the pojo class
 * <p>
 * This class makes use of the following open source libraries to achieve the objective for which the author acknowledges and thanks by mentioning it
 * here.
 * <ol>
 * <li><a href="http://www.javassist.org"><b><i>javassist</i></b> - to create interface object dynamically</a></li>
 * <li><a href="http://www.fuin.org/srcgen4javassist/index.html"><i><b> srcgen4javassist</b></i> - to write the interface source in human readable
 * source code format</a></li>
 * <li><a href="https://github.com/davidmarquis/fluent-interface-proxy"><b><i> fluentinterface</i></b> - for actually generating the proxy builder for
 * the pojo using the interface source file</a></li>
 * </ol>
 * <p>
 * <b>How to use for creating the builder(usually during the maven source generation phase):</b> <br>
 * <br>
 * <code>
 * FluentBuilders builders={@link #create() FluentBuilders.create()};<br>
 * Class[] classes= ...(List of pojo class objects);<br>
 * {@link #writeInterface(Class...) builders.writeInterface(classes)};
 * </code>
 * <p>
 * <b>How to use the auto-generated builder for pojo:</b><br>
 * <br>
 * Assume the following:<br>
 * <ol>
 * <li>A POJO class called ErrorInfo exists that has few properties including
 *  <code>internalErrorCode</code> and <code>serviceErrorCode</code>.</li>
 * <li>An ErrorInfoBuilder for building ErrorInfo is auto-generated during source generation. </li>
 * </ol> 
 * <br>
 * Now,in order to create Builder and use:<br>
 * <br>
 * <code>
 * final ErrorInfoBuilder errBuilder = FluentBuilders.builder(ErrorInfo.class);<br>
 * final ErrorInfo errInfo =
 * errBuilder.setInternalErrorCode(1234L).setServiceErrorCode(2134L).build();
 * </code>
 * 
 * @author venkateshamurthyts@google.com */
public final class FluentBuilders {
    private static final Logger LOGGER = Logger.getLogger(FluentBuilders.class.getCanonicalName());
    /** Command options enum. */
    protected static enum CMDOPTIONS {
        /** List of classes in a file. */
        FILE_LISTING("file", "file-listing"),
        /** List of classes passed in a command option. */
        CLASS_NAMES("cls", "classes"),
        /** Source folder to generate builder. */
        SRC_FOLDER("src", "src-folder-name"),
        /** Set method name pattern. */
        SET_METHOD_PATTERN("set", "set-method-pattern"),
        /** Help option. */
        HELP("h", "help");
        private final String shorter;
        private final String longer;

        CMDOPTIONS(String shortName, String longerName) {
            this.shorter = shortName;
            this.longer = longerName;
        }

        /**
         * Gets short name.
         * @return short name.
         */
        public String shorter() {
            return shorter;
        }

        /**
         * Gets long name.
         * @return long name.
         */
        public String longer() {
            return longer;
        }

        /**
         * Gets option value.
         * @param cmdLine passed
         * @return option value.
         */
        public String option(CommandLine cmdLine) {
            return cmdLine.getOptionValue(longer);
        }

        /**
         * Gets option value.
         * @param cmdLine passed
         * @param defaultValue to be used in case of null
         * @return option value.
         */
        public String option(CommandLine cmdLine, String defaultValue) {
            return cmdLine.getOptionValue(longer, defaultValue);
        }

        /**
         * Gets option is selected.
         * @param cmdLine passed
         * @return true if selected
         */
        public boolean selected(CommandLine cmdLine) {
            return cmdLine.hasOption(longer);
        }
    }

    /** Standard mavenized projects hold sources in this folder. */
    public static final String TYPICAL_SOURCE_FOLDER = "src/main/java";

    /** A typical pattern string for mutator methods prefixed with set/add/put. However the suffix part must represent a property name */
    public static final String TYPICAL_SET_METHOD_PATTERN = "(set|add|put)[a-zA-Z0-9_]+";

    /** An OptionGroup for listing class names or file listing. */
    private static final OptionGroup OPTIONAL_GROUP = new OptionGroup().addOption(
            new Option(CLASS_NAMES.shorter(), CLASS_NAMES.longer(), true, "A comma separated set of canonical POJO class names")).addOption(
            new Option(FILE_LISTING.shorter(), FILE_LISTING.longer(), true,
                    "A text file in the class path that contains each class name (canonical name) in a line"));
    /** A set of command line options. */
    private static final Options CMD_LINE_OPTIONS = new Options()
            .addOption(SET_METHOD_PATTERN.shorter(), SET_METHOD_PATTERN.longer(), true,
                    "A Regular Expression representing the typical setters of a pojo")
            .addOption(SRC_FOLDER.shorter(), SRC_FOLDER.longer(), true, "A Filesystem folder path where the source code will be generated")
            .addOptionGroup(OPTIONAL_GROUP);

    /** A {@link SgClassPool} for writing source content of a class */
    private final SgClassPool sgPool = new SgClassPool();
    /** A {@link ClassPool} that can create a Builder interface given methods */
    private final ClassPool ctPool = new ClassPool(true);
    /** A {@link CtClass} for {@link Builder} from fluentinterface */
    private final CtClass fluentBuilderClass;

    // block
    {
        OPTIONAL_GROUP.setRequired(true);
        try {
            fluentBuilderClass = ctPool.get(Builder.class.getCanonicalName());
        } catch (final NotFoundException e) {
            throw new IllegalStateException("Unable to get Builder class..perhaps not in path", e);
        }
    }
    /** A regex for method name pattern to include in builder. The suffix part of the method and argument must represent the name and type of property */
    private final Pattern setMethodNamePattern;
    /** A file system path where java source files for builder are generated */
    private final File sourceFolderRoot;

    /** A private constructor
     * 
     * @param methodNamePattern to hold the method name pattern for all of which a builder interface needs to be created
     * @param sourceFolder is the file system path to which the files are generated */
    private FluentBuilders(final Pattern methodNamePattern, final File sourceFolder) {
        setMethodNamePattern = methodNamePattern;
        sourceFolderRoot = sourceFolder;
    }

    /** A method to deal with any filtering/transforming the content of SgClass passed. <br>
     * For eg: {@link SgClass} for an interface when directly written adds interface keyword twice which needs to be eliminated
     * 
     * @param sgClass an instance of {@link SgClass}
     * @return processed source content passed {@link SgClass} */
    private String processSgClassContent(final Class<?> clz, final SgClass sgClass) {
        assert sgClass != null;
        return sgClass
                .toString()
                .replace("public abstract interface interface", "public abstract interface")
                .replace("extends com.fluentinterface.builder.Builder", "extends com.fluentinterface.builder.Builder<" + clz.getCanonicalName() + ">");
    }

    /** Writes content to a File
     * 
     * @param file to write content
     * @param content to write to a file */
    private void writeToFile(final File file, final String content) {
        assert file != null;
        file.delete();
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e1) {
            throw new IllegalStateException(e1);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(content);
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /** Add method signature(s) to a builder interface
     * 
     * @param interfaceCtClass the {@link CtClass} representing the Builder interface
     * @param writableMethods the set of {@link CtMethod}s representing the builder methods
     * @return true if the method is a Builder method (such as setXYZ(...))
     * @throws CannotCompileException
     * @throws NotFoundException */
    private void addMethodsToBuilder(final CtClass interfaceCtClass, final Set<CtMethod> writableMethods) throws CannotCompileException,
            NotFoundException {
        for (CtMethod method : writableMethods) {
            interfaceCtClass.addMethod(new CtMethod(interfaceCtClass, method.getName(), 
            		method.getParameterTypes(), interfaceCtClass));
        }
    }

    /** Return true if class passed is <b>not</b> a public class
     * 
     * @param thisPojoClass to be verified if its public
     * @return true if thisPojoClass is <b>not</b> public */
    private boolean isNotAPublicClass(final Class<?> thisPojoClass) {
        return thisPojoClass != null && (Modifier.PUBLIC & thisPojoClass.getModifiers()) != Modifier.PUBLIC;
    }

    /** 
     * Gets a list of writable methods / mutator methods. <br>
     * @param thisPojoClass for which mutator methods must be found
     * @return List of {@link CtMethod}
     * @throws NotFoundException when thisPojoClass is not found */
    private Set<CtMethod> getWritableMethods(final Class<?> thisPojoClass) throws NotFoundException {
        final CtClass ctClass = ctPool.get(thisPojoClass.getName());
        final Set<CtMethod> ctMethodSet = new LinkedHashSet<>(); //Gets collected
        final Set<Class<?>> propTypes = getPropertyClassTypes(thisPojoClass, ctClass, ctMethodSet);

        for (Method method : thisPojoClass.getDeclaredMethods()) {
            if (method.isSynthetic()) {
                LOGGER.log(Level.WARNING, method.getName() + " is synthetically added, so ignoring");
                continue;
            }
            final CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName());
            if (Modifier.isPublic(method.getModifiers())
                    && setMethodNamePattern.matcher(method.getName()).matches() 
                    && !ctMethodSet.contains(ctMethod)) {
                //Make sure the types u get from method is really is of a field type
                boolean isAdded = propTypes.containsAll(Arrays.asList(method.getParameterTypes())) && ctMethodSet.add(ctMethod);
                if (!isAdded) {
					LOGGER.log(Level.WARNING, method.getName() + " is not added"); 
				}
            }
        }
        return ctMethodSet;
    }

    /**
     * Gets class type of field parameters in a class for which setters are to be found
     * @param thisPojoClass
     * @param ctClass
     * @param ctMethodSet can be a subset of setters for fields in this class
     * @return set of classes representing field types
     * @throws NotFoundException
     */
    @SuppressWarnings("serial")
    private Set<Class<?>> getPropertyClassTypes(final Class<?> thisPojoClass, final CtClass ctClass, final Set<CtMethod> ctMethodSet)
            throws NotFoundException {
        Set <Class<?>> set = new LinkedHashSet<>();
        try {
            final Object bean = thisPojoClass.newInstance(); //create an instance
            for (Field field : thisPojoClass.getDeclaredFields()) {
                if (field.isSynthetic()) {
                    LOGGER.log(Level.WARNING,field.getName() + " is syntheticlly added, so ignoring");
                    continue;
                }
                PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, field.getName());
                
                assert pd != null;
                set.add(pd.getPropertyType()); //irrespective of CtMethod just add
                final Method mutator = pd.getWriteMethod();
                if (mutator != null && ctMethodSet.add(ctClass.getDeclaredMethod(mutator.getName()))) {
                    LOGGER.log(Level.INFO, mutator.getName() + " is ADDED");
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new NotFoundException("Exception Not Found:", e);
        }
        return set;
    }

    /** 
     * A default creation method assuming set method pattern as {@value #TYPICAL_SET_METHOD_PATTERN} and the source folder as
     * {@value #TYPICAL_SOURCE_FOLDER}.
     * 
     * @return {@link FluentBuilders} */
    public static FluentBuilders create() {
        return FluentBuilders.create(TYPICAL_SET_METHOD_PATTERN, TYPICAL_SOURCE_FOLDER);
    }

    /** 
     * A creation method using the below parameters.
     * 
     * @param builderMethodNamePattern set method name pattern(regex) such as for eg: {@value #TYPICAL_SET_METHOD_PATTERN}
     * @param sourceFolderName source folder root such as for eg: {@value #TYPICAL_SOURCE_FOLDER}
     * @return {@link FluentBuilders} */
    public static FluentBuilders create(final String builderMethodNamePattern, final String sourceFolderName) {
        if (builderMethodNamePattern == null || builderMethodNamePattern.isEmpty()) {
			throw new IllegalArgumentException("Builder method name pattern must be a valid regex such as " + TYPICAL_SET_METHOD_PATTERN);
		}
        if (sourceFolderName == null || sourceFolderName.isEmpty()) {
			throw new IllegalArgumentException("Source folder name where the files must be generated should be a valid file system path such as "
                    + TYPICAL_SOURCE_FOLDER);
		}
        return new FluentBuilders(Pattern.compile(builderMethodNamePattern), new File(sourceFolderName));
    }

    /** 
     * Get a Builder Proxy from POJO.
     * @param <S> represents a POJO class type
     * @param <T> represents a Builder of type S
     * @param pojoClass is the class to which a builder is needed
     * @return a builder of type pojoBuilder<pojo>
     * @throws ClassNotFoundException 
     */
    
    public static <S, T extends Builder<S>> T builder(final Class<S> pojoClass) throws ClassNotFoundException {
        if (pojoClass == null) {
			throw new IllegalArgumentException("Parameter to this method must be a valid public class obect representing a POJO");
		}
        final String builderInterfaceName = pojoClass.getCanonicalName() + "Builder";
        @SuppressWarnings("unchecked")
        final Class<T> pojoBuilderInterface = (Class<T>) Class.forName(builderInterfaceName);
        return ReflectionBuilder.<T> implementationFor(pojoBuilderInterface).create();
    }

    /** Writes the Builder interface source file for each of the pojo class that has setter methods.<br>
     * <b>Note:It is important to look at the method's return list of classes that could not be built for {@link Builder} </b>
     * 
     * @param pojoClasses is a set of pojo classes for each of which a {@link Builder} interface would be fabricated to a source folder.
     * @return a list of class objects which could not be used for building {@link Builder} interface
     * @throws NotFoundException when CtClass / Class is not found
     * @throws CannotCompileException when non-compilable statements are added
     * @throws IOException when during io errors
     */
    public List<Class<?>> writeInterface(final Class<?>... pojoClasses) throws NotFoundException, CannotCompileException, IOException {
        if (pojoClasses.length == 0) {
			throw new IllegalArgumentException("The Parameters to this method must be an array of valid public class objects");
		}
        final List<Class<?>> failedList = new ArrayList<>();
        for (final Class<?> thisPojoClass : pojoClasses) {

            if (isNotAPublicClass(thisPojoClass)) {
				throw new IllegalArgumentException("The Builders can only be created for a valid public class objects:" + thisPojoClass.getName());
			}

            final Set<CtMethod> writableMethods = getWritableMethods(thisPojoClass);

            if (writableMethods.isEmpty()) {
                failedList.add(thisPojoClass);
            } else {
                final String interfaceName = String.format("%s.%sBuilder", thisPojoClass.getPackage().getName(), thisPojoClass.getSimpleName());
                final CtClass pojoBuilderInterface = ctPool.makeInterface(interfaceName, fluentBuilderClass);

                addMethodsToBuilder(pojoBuilderInterface, writableMethods);
                final SgClass sgClass = SgClass.create(sgPool, pojoBuilderInterface.toClass());
                final File builderSrcFile = new File(sourceFolderRoot, sgClass.getNameAsSrcFilename());
                final String sgClassString = processSgClassContent(thisPojoClass, sgClass);
                writeToFile(builderSrcFile, sgClassString);
            }
        }
        return failedList;
    }

    /** @return {@link #setMethodNamePattern} */
    public Pattern getSetMethodNamePattern() {
        return setMethodNamePattern;
    }

    /** @return {@link #sourceFolderRoot} */
    public File getSourceFolderRoot() {
        return sourceFolderRoot;
    }

    /** @return {@link #fluentBuilderClass} */
    public CtClass getFluentbuilderclass() {
        return fluentBuilderClass;
    }

    /** A main method that accepts a Command line syntax as is represented in {@value #CMD_LINE_OPTIONS}.
     * 
     * @param args need to follow a command line style syntax as indicated by --help option to get more details
     * @throws NotFoundException when CtClass / Class is not found
     * @throws CannotCompileException when non-compilable statements are added
     * @throws IOException when during io errors
     * @throws ClassNotFoundException when CtClass / Class is not found
     * @throws ParseException when command line is un-parsable.
     */
    public static void main(final String[] args) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException,
            ParseException {
        final CommandLineParser parser = new DefaultParser();
        final HelpFormatter formatter = new HelpFormatter();
        final CommandLine cmdLine = parser.parse(CMD_LINE_OPTIONS, args);

        if (HELP.selected(cmdLine)) {
            formatter.printHelp(FluentBuilders.class.getSimpleName() + ":", CMD_LINE_OPTIONS);
            System.exit(0);
        }
        final String classNamesCSV = getClassNamesAsCSV(cmdLine);
        if (classNamesCSV == null || classNamesCSV.isEmpty()) {
            formatter.printHelp(FluentBuilders.class.getSimpleName() + ":", CMD_LINE_OPTIONS);
            throw new IllegalArgumentException("Argument to this program needs to be a comma separated list "
                    + "of well defined, valid and fully qualified POJO class names");
        }

        final String setNamePattern = SET_METHOD_PATTERN.option(cmdLine, TYPICAL_SET_METHOD_PATTERN);
        final String srcFolderName = SRC_FOLDER.option(cmdLine, TYPICAL_SOURCE_FOLDER);

        final FluentBuilders fluentBuilders = FluentBuilders.create(setNamePattern, srcFolderName);
        fluentBuilders.writeInterface(getClassArray(classNamesCSV));
    }

    /** This method checks for class names listing from either the {@value #FILE_LISTING_OPTION} option or from the option {@value #CLASS_NAMES_OPTION}
     * 
     * @param cmdLine
     * @return Class names as CSV
     * @throws IOException */
    private static String getClassNamesAsCSV(final CommandLine cmdLine) throws IOException {
        String classNamesCSV = null;
        if (FILE_LISTING.selected(cmdLine)) {
            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(FILE_LISTING
                    .option(cmdLine))))) {
                final StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
					sb.append(line).append(",");
				}
                classNamesCSV = sb.toString();
            }
        } else if (CLASS_NAMES.selected(cmdLine)) {
			classNamesCSV = CLASS_NAMES.option(cmdLine);
		}
        return classNamesCSV;
    }

    /** Gets class names from a CSV string of class names
     * 
     * @param classNamesCSV
     * @return Class[]
     * @throws ClassNotFoundException */
    private static Class<?>[] getClassArray(final String classNamesCSV) throws ClassNotFoundException {
        assert classNamesCSV != null && !classNamesCSV.isEmpty();
        final String[] classNames = classNamesCSV.split(",");
        final Class<?>[] classList = new Class<?>[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            classList[i] = Class.forName(classNames[i]);
        }
        return classList;
    }

}


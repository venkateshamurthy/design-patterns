package com.github.venkateshamurthy.designpatterns.builders;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

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
 * Assume the following: <ll>
 * <li>A POJO class called ErrorInfo exists that has few properties including <code>internalErrorCode</code> and <code>serviceErrorCode</code>.
 * <li>An ErrorInfoBuilder for building ErrorInfo is auto-generated during source generation. </ll> <br>
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
public class FluentBuilders {
    /** Standard mavenized projects hold sources in this folder */
    public static final String typicalSourceFolderRoot = "src/main/java";
    /** A typical pattern string for all setter methods of an object */
    public static final String typicalSetMethodPattern = "set[a-zA-Z0-9]+";
    /** A {@link SgClassPool} for writing source content of a class */
    private static final SgClassPool sgPool = new SgClassPool();
    /** A {@link ClassPool} that can create a Builder interface given methods */
    private static final ClassPool ctPool = ClassPool.getDefault();
    /** A {@link CtClass} for {@link Builder} from fluentinterface */
    private static final CtClass fluentBuilderClass;
    /** Static block */
    static {
        try {
            fluentBuilderClass = ctPool.get(Builder.class.getCanonicalName());
        } catch (final NotFoundException e) {
            throw new IllegalStateException("Unable to get Builder class..perhaps not in path", e);
        }
    }
    /** A regex for method name pattern to include in builder */
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
        assert !file.exists();
        file.delete();
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e1) {
            throw new IllegalStateException(e1);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(content);
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /** Adds a method signature to a builder interface
     * 
     * @param interfaceCtClass the {@link CtClass} representing the Builder interface
     * @param method the {@link CtMethod} representing the builder method
     * @return true if the method is a Builder method (such as setXYZ(...))
     * @throws CannotCompileException
     * @throws NotFoundException */
    private boolean addMethodToBuilder(final CtClass interfaceCtClass, final CtMethod method) throws CannotCompileException, NotFoundException {
        final boolean isABuilderMethod = setMethodNamePattern.matcher(method.getName()).matches();
        if (isABuilderMethod) {
            interfaceCtClass.addMethod(new CtMethod(interfaceCtClass, method.getName(), method.getParameterTypes(), interfaceCtClass));
        }
        return isABuilderMethod;
    }

    /** Return true if class passed is <b>not</b> a public class
     * 
     * @param thisPojoClass to be verified if its public
     * @return true if thisPojoClass is <b>not</b> public */
    private boolean isNotAPublicClass(final Class<?> thisPojoClass) {
        return thisPojoClass != null && (Modifier.PUBLIC & thisPojoClass.getModifiers()) != Modifier.PUBLIC;
    }

    /** A default creation method assuming set method pattern as {@value #typicalSetMethodPattern} and the source folder as
     * {@value #typicalSourceFolderRoot}
     * 
     * @return {@link FluentBuilders} */
    public static FluentBuilders create() {
        return FluentBuilders.create(typicalSetMethodPattern, typicalSourceFolderRoot);
    }

    /** A creation method using the below parameters
     * 
     * @param builderMethodNamePattern set method name pattern(regex) such as for eg: {@value #typicalSetMethodPattern}
     * @param sourceFolderName source folder root such as for eg: {@value #typicalSourceFolderRoot}
     * @return {@link FluentBuilders} */
    public static FluentBuilders create(final String builderMethodNamePattern, final String sourceFolderName) {
        if (builderMethodNamePattern == null || builderMethodNamePattern.isEmpty())
            throw new IllegalArgumentException("Builder method name pattern must be a valid regex such as " + typicalSetMethodPattern);
        if (sourceFolderName == null || sourceFolderName.isEmpty())
            throw new IllegalArgumentException("Source folder name where the files must be generated should be a valid file system path such as "
                    + typicalSourceFolderRoot);
        return new FluentBuilders(Pattern.compile(builderMethodNamePattern), new File(sourceFolderName));
    }

    /** Get a Builder Proxy from POJO
     * 
     * @param pojoClass is the class to which a builder is needed
     * @return a builder of type pojoBuilder<pojo>
     * @throws ClassNotFoundException */
    public static <S, T extends Builder<S>> T builder(final Class<S> pojoClass) throws ClassNotFoundException {
        if (pojoClass == null)
            throw new IllegalArgumentException("Parameter to this method must be a valid public class obect representing a POJO");
        final String builderInterfaceName = pojoClass.getCanonicalName() + "Builder";
        @SuppressWarnings("unchecked")
        final Class<T> pojoBuilderInterface = (Class<T>) Class.forName(builderInterfaceName);
        return ReflectionBuilder.<T> implementationFor(pojoBuilderInterface).create();
    }

    /** Writes the Builder interface source file for each of the pojo class that has setter methods
     * 
     * @param pojoClasses is a set of pojo classes for each of which a {@link Builder} interface would be fabricated to a source folder.
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException */
    public List<Class<?>> writeInterface(final Class<?>... pojoClasses) throws NotFoundException, CannotCompileException, IOException {
        if (pojoClasses.length == 0)
            throw new IllegalArgumentException("The Parameters to this method must be an array of valid public class objects");
        final List<Class<?>> failedList = new ArrayList<>();
        for (final Class<?> thisPojoClass : pojoClasses) {
            if (isNotAPublicClass(thisPojoClass))
                throw new IllegalArgumentException("The Builders can only be created for a valid public class objects");
            final String interfaceName = thisPojoClass.getPackage().getName() + "." + thisPojoClass.getSimpleName() + "Builder";
            final CtClass pojoBuilderInterface = ctPool.makeInterface(interfaceName, fluentBuilderClass);
            boolean doesThisPojoHasBuilderMethods = false;
            for (final CtMethod method : ctPool.get(thisPojoClass.getName()).getMethods()) {
                doesThisPojoHasBuilderMethods = addMethodToBuilder(pojoBuilderInterface, method) || doesThisPojoHasBuilderMethods;
            }
            if (doesThisPojoHasBuilderMethods) {
                final SgClass sgClass = SgClass.create(sgPool, pojoBuilderInterface.toClass());
                final File builderSrcFile = new File(sourceFolderRoot, sgClass.getNameAsSrcFilename());
                final String sgClassString = processSgClassContent(thisPojoClass, sgClass);
                writeToFile(builderSrcFile, sgClassString);
            } else {
                failedList.add(thisPojoClass);
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
    public static CtClass getFluentbuilderclass() {
        return fluentBuilderClass;
    }

    /** A Test main method
     * 
     * @param args a list of fully qualified class names
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     * @throws ClassNotFoundException */
    public static void main(final String[] args) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
        if (args.length == 0)
            throw new IllegalArgumentException("Argument to this program needs to be a space separated list "
                    + "of well defined, valid and fully qualified POJO class names");
        final String[] classNames = args;
        final Class<?>[] classList = new Class<?>[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            classList[i] = Class.forName(classNames[i]);
        }
        final FluentBuilders fluentBuilders = FluentBuilders.create();
        fluentBuilders.writeInterface(classList);
    }
}
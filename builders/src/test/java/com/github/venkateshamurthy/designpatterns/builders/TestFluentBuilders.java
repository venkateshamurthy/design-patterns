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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

/**
 * The Class TestFluentBuilders.
 */
public class TestFluentBuilders {
    
    /**
	 * The Class pojo1.
	 */
    private static class Pojo1 {
        
        /** The a. */
        private int a;
        
        /**
		 * Gets the a.
		 * 
		 * @return the a
		 */
        @SuppressWarnings("unused")
		public int getA() {
            return a;
        }

        /**
		 * Sets the a.
		 * 
		 * @param a1
		 *            the new a
		 */
        @SuppressWarnings("unused")
		public void setA(int a1) {
            this.a = a1;
        }
    }

    /**
	 * The Class pojo2.
	 */
    private static class Pojo2 {
        
        /** The b. */
    	@SuppressWarnings("unused")
		private String b;
    }

    /**
	 * The Class pojo3.
	 */
    public static class Pojo3 {
        
        /** The a. */
    	private int a;
        
        /** The b. */
    	private String b;

        /**
		 * Gets the a.
		 * 
		 * @return the a
		 */
        public int getA() {
            return a;
        }

        /**
		 * Sets the a.
		 * 
		 * @param a1
		 *            the new a
		 */
        public void setA(int a1) {
            this.a = a1;
        }

        /**
		 * Gets the b.
		 * 
		 * @return the b
		 */
        public String getB() {
            return b;
        }

        /**
		 * Sets the b.
		 * 
		 * @param b1
		 *            the new b
		 */
        public void setB(String b1) {
            this.b = b1;
        }
        
        /**
		 * Sets the junk123.
		 * 
		 * @param junk123
		 *            the new junk123
		 */
        public void setJunk123(Date junk123) {
            
        }
    }
    
    /**
	 * The Class pojo3A.
	 */
    public static class Pojo3A {
        
        /** The a. */
    	private int a;
        
        /** The b. */
    	private String b;

        /**
		 * Gets the a.
		 * 
		 * @return the a
		 */
        public int getA() {
            return a;
        }

        /**
		 * Sets the a.
		 * 
		 * @param a1
		 *            the new a
		 */
        public void setA(int a1) {
            this.a = a1;
        }

        /**
		 * Gets the b.
		 * 
		 * @return the b
		 */
        public String getB() {
            return b;
        }

        /**
		 * Sets the b.
		 * 
		 * @param b1
		 *            the new b
		 */
        public void setB(String b1) {
            this.b = b1;
        }
        
        /**
		 * Sets the junk123.
		 * 
		 * @param junk123
		 *            the new junk123
		 */
        public void setJunk123(Date junk123) {
        	
        }
    }
    
    /**
	 * The Class pojo3B.
	 */
    public static class Pojo3B {
        
        /** The a. */
    	private  int a;
        
        /** The b. */
    	private String b;

        /**
		 * Gets the a.
		 * 
		 * @return the a
		 */
        public int getA() {
            return a;
        }

        /**
		 * Sets the a.
		 * 
		 * @param a1
		 *            the new a
		 */
        public void setA(int a1) {
            this.a = a1;
        }

        /**
		 * Gets the b.
		 * 
		 * @return the b
		 */
        public String getB() {
            return b;
        }

        /**
		 * Sets the b.
		 * 
		 * @param b1
		 *            the new b
		 */
        public void setB(String b1) {
            this.b = b1;
        }
        
        /**
		 * Sets the junk123.
		 * 
		 * @param junk123
		 *            the new junk123
		 */
        public void setJunk123(Date junk123) {
        	
        }
    }
    
    /**
	 * The Class pojo4.
	 */
    public static class Pojo4 {
        
        /** The a. */
    	private  int a;
        
        /** The b. */
    	private String b;

        /**
		 * Gets the a.
		 * 
		 * @return the a
		 */
        public int getA() {
            return a;
        }

        /**
		 * Sets the a.
		 * 
		 * @param a1
		 *            the new a
		 */
        public void setA(int a1) {
            this.a = a1;
        }

        /**
		 * Gets the b.
		 * 
		 * @return the b
		 */
        public String getB() {
            return b;
        }

        /**
		 * Sets the b.
		 * 
		 * @param b1
		 *            the new b
		 */
        public void setB(String b1) {
            this.b = b1;
        }

    }
    
    /**
	 * The Class pojo4A.
	 */
    public static class Pojo4A {
        
        /** The a. */
    	private int a;
        
        /** The b. */
    	private  String b;

        /**
		 * Gets the a.
		 * 
		 * @return the a
		 */
        public int getA() {
            return a;
        }

        /**
		 * Sets the a.
		 * 
		 * @param a1
		 *            the new a
		 */
        public void setA(int a1) {
            this.a = a1;
        }

        /**
		 * Gets the b.
		 * 
		 * @return the b
		 */
        public String getB() {
            return b;
        }

        /**
		 * Sets the b.
		 * 
		 * @param b1
		 *            the new b
		 */
        public void setB(String b1) {
            this.b = b1;
        }

    }
    
    /**
	 * The Class pojo4B.
	 */
    public static class Pojo4B {
        
        /** The a. */
    	private int a;
        
        /** The b. */
    	private  String b;

        /**
		 * Gets the a.
		 * 
		 * @return the a
		 */
        public int getA() {
            return a;
        }

        /**
		 * Sets the a.
		 * 
		 * @param a1
		 *            the new a
		 */
        public void setA(int a1) {
            this.a = a1;
        }

        /**
		 * Gets the b.
		 * 
		 * @return the b
		 */
        public String getB() {
            return b;
        }

        /**
		 * Sets the b.
		 * 
		 * @param b1
		 *            the new b
		 */
        public void setB(String b1) {
            this.b = b1;
        }

    }
    
    /**
	 * The Class pojo5.
	 */
    public static class Pojo5 {
        
        /** The a. */
    	private int a;
        
        /** The b. */
    	private  String b;

        /**
		 * Gets the a.
		 * 
		 * @return the a
		 */
        public int getA() {
            return a;
        }

        /**
		 * Gets the b.
		 * 
		 * @return the b
		 */
        public String getB() {
            return b;
        }

    }
    
    /**
	 * Test main error.
	 * 
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws NotFoundException
	 *             the not found exception
	 * @throws CannotCompileException
	 *             the cannot compile exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParseException
	 *             the parse exception
	 */
    @Test(expected = IllegalArgumentException.class)
    public void testMainError() throws ClassNotFoundException, NotFoundException, CannotCompileException, IOException, ParseException {
        String cmd = "-set set[a-zA-Z0-9]+ -src src/generated/java ";
        FluentBuilders.main(cmd.split(" "));
    }
    
    /**
	 * Test main with file listing.
	 * 
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws NotFoundException
	 *             the not found exception
	 * @throws CannotCompileException
	 *             the cannot compile exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParseException
	 *             the parse exception
	 */
    @Test
    public void testMainWithFileListing() throws ClassNotFoundException, NotFoundException, CannotCompileException, IOException, ParseException {
        String cmd = "-set set[a-zA-Z0-9]+ -src src/generated/java -file class-listings.txt";
        File file3 = getFile(new File("src/generated/java"), Pojo3B.class);
        File file4 = getFile(new File("src/generated/java"), Pojo4B.class);
        file3.delete();
        file4.delete();
        FluentBuilders.main(cmd.split(" "));
        
        Assert.assertTrue(file3.exists());
        Assert.assertTrue(file4.exists());
        BufferedReader r = new BufferedReader(new FileReader(file3));
        String l;
        while ((l = r.readLine()) != null) {
            System.out.println(l);
        }
        file3.delete();
        file4.delete();
    }
    
    /**
	 * Test main with class names.
	 * 
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws NotFoundException
	 *             the not found exception
	 * @throws CannotCompileException
	 *             the cannot compile exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParseException
	 *             the parse exception
	 */
    @Test
    public void testMainWithClassNames() throws ClassNotFoundException, NotFoundException, CannotCompileException, IOException, ParseException {
        String cmd = "-set set[a-zA-Z0-9]+ -src src/generated/java -cls " + Pojo3A.class.getName() + "," + Pojo4A.class.getName();
        File file3 = getFile(new File("src/generated/java"), Pojo3A.class);
        File file4 = getFile(new File("src/generated/java"), Pojo4A.class);
        file3.delete();
        file4.delete();
        FluentBuilders.main(cmd.split(" "));
        
        Assert.assertTrue(file3.exists());
        Assert.assertTrue(file4.exists());
        file3.delete();
        file4.delete();
    }
    
    /**
	 * Test create.
	 * 
	 * @throws NotFoundException
	 *             the not found exception
	 * @throws CannotCompileException
	 *             the cannot compile exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    @Test
    public void testCreate() throws NotFoundException, CannotCompileException, IOException {
        FluentBuilders fluentBuilderGenerator = FluentBuilders.create();
        Assert.assertNotNull(fluentBuilderGenerator);
        Assert.assertNotNull(fluentBuilderGenerator.getSourceFolderRoot());
        Assert.assertNotNull(fluentBuilderGenerator.getSetMethodNamePattern());
        Assert.assertNotNull(fluentBuilderGenerator.getFluentbuilderclass());
        Assert.assertEquals(new File(FluentBuilders.TYPICAL_SOURCE_FOLDER), fluentBuilderGenerator.getSourceFolderRoot());
        Assert.assertEquals(FluentBuilders.TYPICAL_SET_METHOD_PATTERN, fluentBuilderGenerator.getSetMethodNamePattern().pattern());
        File file1 = getFile(fluentBuilderGenerator.getSourceFolderRoot(), Pojo1.class);
        File file2 = getFile(fluentBuilderGenerator.getSourceFolderRoot(), Pojo2.class);

        try {
            fluentBuilderGenerator.writeInterface(Pojo1.class, Pojo2.class);
        } catch (IllegalArgumentException iae) {
            // ignore as this is expected since pojo1 and pojo2 are non public class and hence wont exist so assert that
            Assert.assertFalse(file1.exists());
            Assert.assertFalse(file2.exists());
        }
        List<Class<?>> failedList = fluentBuilderGenerator.writeInterface(Pojo3.class, Pojo4.class);
        Assert.assertTrue(failedList.isEmpty());
        File file3 = getFile(fluentBuilderGenerator.getSourceFolderRoot(), Pojo3.class);
        File file4 = getFile(fluentBuilderGenerator.getSourceFolderRoot(), Pojo4.class);
        Assert.assertTrue(file3.exists());
        Assert.assertTrue(file4.exists());

        failedList = fluentBuilderGenerator.writeInterface(Pojo5.class);
        Assert.assertEquals(1, failedList.size());
        Assert.assertEquals(Pojo5.class, failedList.get(0));

        file1.delete();
        file2.delete();
        file3.delete();
        file4.delete();

    }

    /**
	 * Gets the file.
	 * 
	 * @param baseFolder
	 *            the base folder
	 * @param pojo
	 *            the pojo
	 * @return the file
	 */
    private File getFile(File baseFolder, Class<?> pojo) {
        return new File(baseFolder, 
                pojo.getPackage().getName().replace('.', '/') + "/" + pojo.getSimpleName()
                + "Builder.java");
    }

}

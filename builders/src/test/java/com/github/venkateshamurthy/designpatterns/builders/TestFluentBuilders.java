package com.github.venkateshamurthy.designpatterns.builders;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.apache.commons.cli.ParseException;
import org.junit.After;
import org.junit.Test;

public class TestFluentBuilders {
    private static class pojo1 {
        int a;
        String b;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }

    private static class pojo2 {
        int a;
        String b;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }

    public static class pojo3 {
        int a;
        String b;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }
    public static class pojo3A {
        int a;
        String b;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }
    public static class pojo3B {
        int a;
        String b;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }
    public static class pojo4 {
        int a;
        String b;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }
    public static class pojo4A {
        int a;
        String b;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }
    
    public static class pojo4B {
        int a;
        String b;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }
    public static class pojo5 {
        int a;
        String b;

        public int getA() {
            return a;
        }

        public String getB() {
            return b;
        }

    }
    @Test(expected=IllegalArgumentException.class)
    public void testMainError() throws ClassNotFoundException, NotFoundException, CannotCompileException, IOException, ParseException{
        String cmd="-set set[a-zA-Z0-9]+ -src src/generated/java ";
        FluentBuilders.main(cmd.split(" "));
    }
    @Test
    public void testMainWithFileListing() throws ClassNotFoundException, NotFoundException, CannotCompileException, IOException, ParseException{
        String cmd="-set set[a-zA-Z0-9]+ -src src/generated/java -file class-listings.txt";
        File file3 = getFile(new File("src/generated/java"), pojo3B.class);
        File file4 = getFile(new File("src/generated/java"), pojo4B.class);
        file3.delete();
        file4.delete();
        FluentBuilders.main(cmd.split(" "));
        
        Assert.assertTrue(file3.exists());
        Assert.assertTrue(file4.exists());
        file3.delete();
        file4.delete();
    }
    @Test
    public void testMainWithClassNames() throws ClassNotFoundException, NotFoundException, CannotCompileException, IOException, ParseException{
        String cmd="-set set[a-zA-Z0-9]+ -src src/generated/java -cls "+pojo3A.class.getName()+","+pojo4A.class.getName();
        File file3 = getFile(new File("src/generated/java"), pojo3A.class);
        File file4 = getFile(new File("src/generated/java"), pojo4A.class);
        file3.delete();
        file4.delete();
        FluentBuilders.main(cmd.split(" "));
        
        Assert.assertTrue(file3.exists());
        Assert.assertTrue(file4.exists());
        file3.delete();
        file4.delete();
    }
    @Test
    public void testCreate() throws NotFoundException, CannotCompileException, IOException {
        FluentBuilders fluentBuilderGenerator = FluentBuilders.create();
        Assert.assertNotNull(fluentBuilderGenerator);
        Assert.assertNotNull(fluentBuilderGenerator.getSourceFolderRoot());
        Assert.assertNotNull(fluentBuilderGenerator.getSetMethodNamePattern());
        Assert.assertNotNull(fluentBuilderGenerator.getFluentbuilderclass());
        Assert.assertEquals(FluentBuilders.typicalSourceFolderRoot, fluentBuilderGenerator.getSourceFolderRoot().getPath());
        Assert.assertEquals(FluentBuilders.typicalSetMethodPattern, fluentBuilderGenerator.getSetMethodNamePattern().pattern());
        File file1=getFile(fluentBuilderGenerator.getSourceFolderRoot(),pojo1.class);
        File file2=getFile(fluentBuilderGenerator.getSourceFolderRoot(),pojo2.class);

        try {
            fluentBuilderGenerator.writeInterface(pojo1.class, pojo2.class);
        } catch (IllegalArgumentException iae) {
            // ignore as this is expected since pojo1 and pojo2 are non public class and hence wont exist so assert that
            Assert.assertFalse(file1.exists());
            Assert.assertFalse(file2.exists());
        }
        List<Class<?>> failedList = fluentBuilderGenerator.writeInterface(pojo3.class, pojo4.class);
        Assert.assertTrue(failedList.isEmpty());
        File file3 = getFile(fluentBuilderGenerator.getSourceFolderRoot(), pojo3.class);
        File file4 = getFile(fluentBuilderGenerator.getSourceFolderRoot(), pojo4.class);
        Assert.assertTrue(file3.exists());
        Assert.assertTrue(file4.exists());

        failedList = fluentBuilderGenerator.writeInterface(pojo5.class);
        Assert.assertEquals(1, failedList.size());
        Assert.assertEquals(pojo5.class, failedList.get(0));

        file1.delete();
        file2.delete();
        file3.delete();
        file4.delete();

    }

    private File getFile(File baseFolder, Class<?> pojo) {
        return new File(baseFolder, 
                pojo.getPackage().getName().replace('.', '/') + "/" + pojo.getSimpleName()
                + "Builder.java");
    }

}

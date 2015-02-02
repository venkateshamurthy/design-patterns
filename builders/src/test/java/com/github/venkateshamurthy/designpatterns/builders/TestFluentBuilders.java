package com.github.venkateshamurthy.designpatterns.builders;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import junit.framework.Assert;

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
    @Test
    public void testCreate() throws NotFoundException, CannotCompileException, IOException {
        FluentBuilders fluentBuilderGenerator = FluentBuilders.create();
        Assert.assertNotNull(fluentBuilderGenerator);
        Assert.assertNotNull(fluentBuilderGenerator.getSourceFolderRoot());
        Assert.assertNotNull(fluentBuilderGenerator.getSetMethodNamePattern());
        Assert.assertNotNull(fluentBuilderGenerator.getFluentbuilderclass());
        Assert.assertEquals(FluentBuilders.typicalSourceFolderRoot, fluentBuilderGenerator.getSourceFolderRoot().getPath());
        Assert.assertEquals(FluentBuilders.typicalSetMethodPattern, fluentBuilderGenerator.getSetMethodNamePattern().pattern());
        File file1=getFile(fluentBuilderGenerator,pojo1.class);
        File file2=getFile(fluentBuilderGenerator,pojo2.class);

        try {
            fluentBuilderGenerator.writeInterface(pojo1.class, pojo2.class);
        } catch (IllegalArgumentException iae) {
            // ignore as this is expected since pojo1 and pojo2 are non public class and hence wont exist so assert that
            Assert.assertFalse(file1.exists());
            Assert.assertFalse(file2.exists());
        }
        List<Class<?>> failedList = fluentBuilderGenerator.writeInterface(pojo3.class, pojo4.class);
        Assert.assertTrue(failedList.isEmpty());
        File file3 = getFile(fluentBuilderGenerator, pojo3.class);
        File file4 = getFile(fluentBuilderGenerator, pojo4.class);
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

    private File getFile(FluentBuilders fluentBuilderGenerator, Class<?> pojo) {
        return new File(fluentBuilderGenerator.getSourceFolderRoot(), 
                pojo.getPackage().getName().replace('.', '/') + "/" + pojo.getSimpleName()
                + "Builder.java");
    }

}

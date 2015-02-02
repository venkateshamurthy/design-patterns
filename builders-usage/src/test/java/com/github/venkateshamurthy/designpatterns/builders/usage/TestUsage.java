package com.github.venkateshamurthy.designpatterns.builders.usage;

import org.junit.Assert;
import org.junit.Test;

import com.github.venkateshamurthy.designpatterns.builders.FluentBuilders;
import com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClass;
import com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClassBuilder;

public class TestUsage {

    @Test
    public void test() throws ClassNotFoundException {
        final int a=1050;
        final String b="Sandeep";
        PojoClass pojo=FluentBuilders.<PojoClass,PojoClassBuilder>builder(PojoClass.class).setA(a).setB(b).build();
        Assert.assertEquals(a,pojo.getA());
        Assert.assertEquals(b, pojo.getB());
    }

}

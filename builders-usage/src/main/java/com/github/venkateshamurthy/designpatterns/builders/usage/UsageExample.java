package com.github.venkateshamurthy.designpatterns.builders.usage;

import com.github.venkateshamurthy.designpatterns.builders.FluentBuilders;
import com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClass;
import com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClassBuilder;


public class UsageExample {
    public static void main(String[] args) throws ClassNotFoundException {
        PojoClassBuilder builder=FluentBuilders.builder(PojoClass.class);
        PojoClass pojo=builder.setA(1050).setB("Sandeep").build();
        System.out.println(pojo);
    }
}

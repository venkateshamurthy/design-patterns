A project to explore design patterns. Each pattern may contain module and usually a usage module would be present if there is auto-generate phase.

Note:
-----
This project may utilize other open source libraries to fit the gaps and acknowledges those authors for using it. 

Builder:
--------
Two modules builders and builders-usage to demonstrate auto-generation of interface for builder and a usage of generated interface to crate a proxy based fluent builder.

My special thanks to the following projects and their authors:

   *1. fluent-interface-proxy(https://github.com/davidmarquis/fluent-interface-proxy), 
   
   *2. srcgen4javassist
   
   *3. javassist

How to compile and test:

To create and install builders module: mvn --projects builders clean install

To create proxy interface and test usage: mvn --projects builders-usage clean test

Example usage:

Assume a simple pojo class that void returning set methods as follows:
```java
package com.github.venkateshamurthy.designpatterns.builders.examples.pojo;

public class PojoClass {
    int a;
    String b;
    public PojoClass() {
        super();
    }
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

    public String toString() {
        return getClass().getSimpleName() + ":" + "a=" + a + " b=" + b;
    }

    public boolean equals(Object o) {
        if (o != null) {
            PojoClass that = (PojoClass) o;
            return that.a == a && (b != null && b.equals(that.b));
        }
        return false;
    }
}
```
Now to create the builder interface from the above pojo: Reach upto the folder representing builders-usage.
type in : mvn clean compile 

This creates a PojoClassBuilder.java in the same package/folder as of PojoClass.java but in the builders-usage project
```java
package com.github.venkateshamurthy.designpatterns.builders.examples.pojo;

public abstract interface PojoClassBuilder extends com.fluentinterface.builder.Builder<com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClass> {
public abstract com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClassBuilder setA(int p0);
public abstract com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClassBuilder setB(java.lang.String p0);

}
```
Now to create a proxy interface and test a simple use: 
```java
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
```
type in : mvn clean test

A few test methods of usage:
```java
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
```
 
Visitor:
-------
TBD

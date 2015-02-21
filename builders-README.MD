# Builder: 
##### [Click here for the blog](http://vemurthy.blogspot.com/2015/02/builder-pattern.html)

Many a times; the POJOs that is to be used are generated code from some tool/utility and hence may not be always possible to derive / inherit / add code to the already generated  code.

When the need arises to construct a reasonably complex object; a builder for the POJO is necessary but would be better served if this builder was also genarated as part of POJO (a wishful thinking).

Given this context; if a Builder needs to be added to an already generated code for POJO; one of the approaches would be to 

  1. Create an Interface and add fluent setter methods to the fields. The interface additionally needs to provide a public <T> T build() method.
 
  2. Next,  use any means(may be proxy) to generate a Pojo specific Builder implementation adhering to the interface for the client's use.

For step 1; I am utilizing the open source libraries javassist and srcgen4javassist/codemodel to create the interface and for step 2; i am using fluent-interface-proxy.
   
The module builders deals with provisioning the interface and wrapper methods to create the builder and builders-usage to demonstrate auto-generation of interface for builder and a usage of generated interface to crate a proxy based fluent builder.

My special thanks to the following projects and their authors:

  1. [fluent-interface-proxy](https://github.com/davidmarquis/fluent-interface-proxy) [license:](http://opensource.org/licenses/MIT)
   
  2. [codemodel](https://codemodel.java.net/) [license:](https://java.net/projects/codemodel/sources/svn/content/trunk/codemodel/LICENSE.html?rev=333)
   
  3. [javassist](https://github.com/jboss-javassist/javassist) [license:] (https://github.com/jboss-javassist/javassist/blob/master/License.html "Apache 2.0")

How to compile and test:

To create and install builders module: mvn --settings ./settings.xml --projects builders clean install

To create proxy interface and test usage: mvn --settings ./settings.xml --projects builders-usage clean test

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
----------------------------

This usage just requires you to provide a -file option whose value is a file in the class path containing list of fully qualified class names such as follows

Eg: class-listings.txt

```java
com.github.venkateshamurthy.designpatterns.builders.TestFluentBuilders$Pojo3B
com.github.venkateshamurthy.designpatterns.builders.TestFluentBuilders$Pojo4B
```
Now use the file name in the class path in the argument as follows.
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
```

An another way could be to just to use the -cls option (can be handly if there are just few handful classes)
```java
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

### How to include in maven as an example:
Make sure to have a file in the class path containing all the fully qualified class names (each class in a line). 
So presuming this to be src/main/resources/class-listing.txt ; just ensure that the resources section is configuered to copy this as resource.
#### Pojo1.java for the example of Pojo class
```java
package com.github.venkateshamurthy.designpatterns.builders.usage;

public class Pojo1 {
    public Pojo1() {
        super();
        // TODO Auto-generated constructor stub
    }
    public int getI() {
        return i;
    }
    public void setI(int i) {
        this.i = i;
    }
    public String getJ() {
        return j;
    }
    public void setJ(String j) {
        this.j = j;
    }
    public String toString() {
        return getClass().getSimpleName()+":i="+i+" j="+j;
    }
    int i;
    String j;    
}
```
#### src/main/resources/class-listing.txt
```java
com.github.venkateshamurthy.designpatterns.builders.usage.Pojo1
```

#### TestBuildersUsage as a test code that uses compiled class of generated builder class
```java
package com.github.venkateshamurthy.designpatterns.builders.usage;

import org.junit.Test;

import com.github.venkateshamurthy.designpatterns.builders.FluentBuilders;

public class TestBuildersUsage {
    @Test
    public void test() throws ClassNotFoundException {
       Pojo1 pojo1=FluentBuilders.<Pojo1,Pojo1Builder>builder(Pojo1.class)
                       .setI(10).setJ("Murthy").build();
       System.out.println(pojo1.toString());
    }
}
```

#### pom.xml in this example could be as follows:

```java
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.github.venkateshamurthy</groupId>
    <artifactId>builders-test</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <properties>
        <generatedSourcesDirectory>src/generated/java</generatedSourcesDirectory>
    </properties>
    <dependencies>
        <dependency>
            <groupId>com.github.venkateshamurthy</groupId>
            <artifactId>builders</artifactId>
            <version>1.0-SNAPSHOT</version>
            <classifier>jar-with-dependencies</classifier>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.5</version>
        </dependency>
    </dependencies>
    <repositories>
        <repository>
            <id>oss-snapshot</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        </repository>
        <repository>
            <id>apache-snapshots</id>
            <url>https://repository.apache.org/content/repositories/snapshots</url>
        </repository>
    </repositories>
    <build>
        <sourceDirectory>src/main/java</sourceDirectory>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>class-listing.txt</include>
                </includes>
            </resource>
            <resource>
                <directory>${generatedSourcesDirectory}</directory>
                <includes>
                    <include>**/*.java</include>
                </includes>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <artifactId>maven-clean-plugin</artifactId>
                <version>2.6.1</version>
                <configuration>
                    <filesets>
                        <fileset>
                            <directory>${generatedSourcesDirectory}</directory>
                            <includes>
                                <include>**/*.java</include>
                            </includes>
                            <followSymlinks>false</followSymlinks>
                        </fileset>
                    </filesets>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>1.3.2</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>exec</goal>
                        </goals>
                        <phase>process-classes</phase>
                    </execution>
                </executions>
                <configuration>
                    <executable>${java.home}/bin/java</executable>
                    <workingDirectory>${project.build.dir}</workingDirectory>
                    <arguments>
                        <argument>-classpath</argument>
                        <classpath />
                        <argument>com.github.venkateshamurthy.designpatterns.builders.FluentBuilders</argument>
                        <argument>-src</argument>
                        <argument>${generatedSourcesDirectory}</argument>
                        <!-- Use a -cls argument or -file argument -->
                        <!--
                        <argument>-cls</argument>
                        <argument>com.github.venkateshamurthy.designpatterns.builders.usage.Pojo1</argument>
                        -->
                        <argument>-file</argument>
                        <argument>class-listing.txt</argument>
                    </arguments>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <version>1.7</version>
                <executions>
                    <execution>
                        <id>add-source</id>
                        <phase>process-classes</phase>
                        <goals>
                            <goal>add-source</goal>
                        </goals>
                        <configuration>
                            <sources>
                                <source>${generatedSourcesDirectory}</source>
                            </sources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>2.3.2</version>
                <executions>
                    <execution>
                       <!--  This first pass will compile Pojo1 and any other existing java files -->
                        <id>first-pass-compile</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration></configuration>
                    </execution>
                    <execution>
                       <!--  Second pass compilation is required for sources that are generated post compile phase(For eg: Pojo1Builder.java)  -->
                        <id>second-pass-compile-generated-sources</id>
                        <phase>process-classes</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <generatedSourcesDirectory>${generatedSourcesDirectory}</generatedSourcesDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

Builder with AutoValue and Lombok:
----------------------------------
This is an example condensate with Google auto value. Here the POJO class needs to provide inner static abstract class with each attribute/property exposed an abstract method 
```java
package com.github.venkateshamurthy.designpatterns.builders.autovalue;
import com.google.auto.value.AutoValue;

/**
 * Type PojoClass.
 * 
 * @author vemurthy
 * 
 */
public final class PojoClass {
    
	@AutoValue
	abstract static class Animal {
		static Animal create(String name, int numberOfLegs) {
			return new AutoValue_PojoClass_Animal(name, numberOfLegs);
		}
		abstract String name();
		abstract int numberOfLegs();
	}
}

```
Usage of autovalue based builder:
```java
package com.github.venkateshamurthy.designpatterns.builders.autovalue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.venkateshamurthy.designpatterns.builders.autovalue.PojoClass.Animal;

/**
 * Type PojoClassTest.
 * @author vemurthy
 *
 */
public class PojoClassTest {

	/**
	 * Tests Animal Builder.
	 */
	@Test
	public void testAnimal() {
	    final int legs = 4;
		Animal dog = Animal.create("dog", legs);
		assertEquals("dog", dog.name());
		assertEquals(legs, dog.numberOfLegs());
		// // You really don't need to write tests like these; just
		// illustrating.
		assertTrue(Animal.create("dog", legs).equals(dog));
		assertFalse(Animal.create("cat", legs).equals(dog));
		assertFalse(Animal.create("dog", legs - 1).equals(dog));
		assertEquals("Animal{name=dog, numberOfLegs=4}", dog.toString());
		PojoClass pojo = new PojoClass();
		assertNotNull(pojo);
	}

}
```


The following is a lombok way of doing things and looks much leaner comparatively:
```java
package com.github.venkateshamurthy.designpatterns.builders.lombok;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Example {
    private int foo;
    private String bar;
}
```
OR with an value adding validating Builder as follows. This basically builds an extended Builder that adds validation as well.
```java
package com.github.venkateshamurthy.designpatterns.builders.lombok;

import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

/**
 * Type Example.
 * @author vemurthy
 *
 */
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log
public class Example {
    private int foo;    //This is private final
    private String bar; //This is private final
    
    /**
     * Type Builder for validating parameters.
     * @author vemurthy
     *
     */
    @Data(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Builder extends ExampleBuilder {
        private static final Pattern BAR_PATTERN = Pattern.compile("[a-zA-z0-9]+");
        @Override
        public ExampleBuilder bar(String bar) {
            if (bar == null || !BAR_PATTERN.matcher(bar).matches()) {
                throw new IllegalArgumentException("bar variable needs to follow pattern:" + BAR_PATTERN);
            }
            return super.bar(bar);
        }
        
        @Override
        public ExampleBuilder foo(int foo) {
            if (foo < 0) {
                throw new IllegalArgumentException("foo needs to be positive");
            }
            return super.foo(foo);
        }
        
        /* (non-Javadoc)
         * @see com.github.venkateshamurthy.designpatterns.builders.lombok.Example.ExampleBuilder#build()
         */
        /**{@inheritDoc}.*/
        public Example build() {
            log.info("Building with lombok...");
            return super.build();
        }
    }
    /**
     * Builder creator.
     * @return {@link Builder}
     */
    public static Builder builder() {
        return Builder.of();
    }
}

```
Usage of lombok based builders:
```java
package com.github.venkateshamurthy.designpatterns.builders.lombok;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class TestExample.
 */
public class TestExample {
    /**
     * test string.
     */
    private final String test = "test";
    /**
     * Testing first.
     */
    @Test
    public void test() {
        Example example = Example.builder().foo(Integer.MAX_VALUE).bar(test).build();
        Example example1 = Example.builder().foo(Integer.MIN_VALUE).bar(test + "1").build();
        Assert.assertEquals(Integer.MAX_VALUE, example.getFoo());
        Assert.assertEquals(test, example.getBar());
        Assert.assertEquals(example1, example1);
        Assert.assertTrue(example1.canEqual(example1));
        Assert.assertFalse(example.equals(example1));
        Assert.assertEquals(example.toString(), example.toString());
        Assert.assertNotSame(example1.toString(), example.toString());
        Assert.assertEquals(example.hashCode(), example.hashCode());
        Assert.assertNotSame(example1.hashCode(), example.hashCode());
        Assert.assertFalse(example.equals(null));
        
    }
    
    /**
     * Test for illegal asserts for foo.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalFoo() {
        Example example = Example.builder().foo(-1).bar(test).build();
    }
    
    /**
     * Test for illegal asserts for bar.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalBar() {
        Example example = Example.builder().foo(Integer.MAX_VALUE).bar(null).build();
    }
}
```

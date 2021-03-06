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
        Example example1 = Example.builder().foo(Integer.SIZE).bar(test + "1").build();
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


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

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClass;

/**
 * Type TestPojo.
 * 
 * @author vemurthy
 * 
 */
public class TestPojo {

    /**
     * test method.
     */
    @Test
    public void test() {
        PojoClass pojo = new PojoClass();
        pojo.setA(Integer.MAX_VALUE);
        pojo.setB(null);
        Assert.assertNull(pojo.getB());
        Assert.assertEquals(Integer.MAX_VALUE, pojo.getA());
        pojo.setToList(Collections.<String> emptyList());
        Assert.assertEquals(Collections.<String> emptyList(), pojo.getToList());
        pojo.setJunk123(null);
        Assert.assertEquals(pojo, pojo);
        Assert.assertEquals(pojo.hashCode(), pojo.hashCode());
        Assert.assertEquals(pojo.toString(), pojo.toString());
        PojoClass pojo1 = new PojoClass();
        pojo1.setB("");
        Assert.assertFalse(pojo.equals(pojo1));
        pojo1.setA(Integer.MAX_VALUE);
        pojo.setB("");
        pojo1.setToList(Collections.<String> emptyList());
        Assert.assertTrue(pojo.equals(pojo1));
        Assert.assertFalse(pojo.equals(null));
        pojo1.setToList(Collections.singletonList(""));
        Assert.assertFalse(pojo.equals(pojo1));
        pojo1.setB("that");
        Assert.assertFalse(pojo.equals(pojo1));
        Assert.assertNotSame(pojo.hashCode(), pojo1.hashCode());
        
        
    }

}

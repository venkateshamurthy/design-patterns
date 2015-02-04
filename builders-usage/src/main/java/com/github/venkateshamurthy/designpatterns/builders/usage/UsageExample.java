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
package com.github.venkateshamurthy.designpatterns.builders.usage;

import java.util.Collections;

import com.github.venkateshamurthy.designpatterns.builders.FluentBuilders;
import com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClass;
import com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClassBuilder;


/**
 * The Class UsageExample.
 */
public class UsageExample {
    
    /**
	 * INT_1050
	 */
	private static final int INT_1050 = 1050;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
    public static void main(String[] args) throws ClassNotFoundException {
        PojoClassBuilder builder = FluentBuilders.builder(PojoClass.class);
        PojoClass pojo = builder.setA(INT_1050).setB("Sandeep").setToList(Collections.<String>singletonList("Deepsan")).build();
        System.out.println(pojo);
    }
}

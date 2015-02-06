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

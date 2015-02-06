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
package com.github.venkateshamurthy.designpatterns.builders.examples.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * A Demonstration example pojo that could be used to show builder interface generation.
 * @author vemurthy
 */
public class PojoClass {
    private int a;
    private String b;
    private List<String> toList = new ArrayList<>();
    
    /**
	 * Instantiates a new pojo class.
	 */
    public PojoClass() {
        super();
    }
    /**
     * Gets a.
     * @return a
     */
    public int getA() {
        return a;
    }
    /**
     * Sets a.
     * @param a1 to set
     */
    public void setA(int a1) {
        this.a = a1;
    }
    /**
     * Gets b.
     * @return b
     */
    public String getB() {
        return b;
    }
    
    /**
	 * Sets the b.
	 * 
	 * @param b1
	 *            the new b
	 */
    public void setB(String b1) {
        this.b = b1;
    }
    
    /**
	 * Gets the to list.
	 * 
	 * @return the to list
	 */
    public List<String> getToList() {
        return toList;
    }
    
    /**
	 * Sets the to list.
	 * 
	 * @param toList1
	 *            the new to list
	 */
    public void setToList(List<String> toList1) {
        this.toList = toList1;
    }
    
    /**
	 * Sets the junk123.
	 * 
	 * @param junk1234
	 *            the new junk123
	 */
    public void setJunk123(Date junk1234) {
        
    }
    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + "a=" + a + " b=" + b + " list=" + toList;
    }
    @Override
    public int hashCode() {
    	return a + (b != null ? b.hashCode() : 0);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (this == o) {
                return true;
            }
            PojoClass that = (PojoClass) o;
            return that.a == a && (b != null && b.equals(that.b)) && toList != null && toList.equals(that.toList);
        }
        return false;
    }
    
}


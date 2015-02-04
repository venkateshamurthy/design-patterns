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
package com.github.venkateshamurthy.designpatterns.builders.pojobuilder;

/**
 * Type PojoClass for demonstration of builder using annotations
 * 
 * @author vemurthy
 * 
 */
@AnnBuilder
public class PojoClass {
	/**
	 * name
	 */
	private String name;
	/**
	 * address
	 */
	private String address;
	/**
	 * company
	 */
	private String company;
	/**
	 * Gets the name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the passed in name to the field name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the address.
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Sets the passed in address to the field address
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * Gets the company.
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * Sets the passed in company to the field company
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
}

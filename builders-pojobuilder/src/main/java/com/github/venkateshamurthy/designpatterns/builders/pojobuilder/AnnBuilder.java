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

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * An meta annotation composed of {@link GeneratePojoBuilder} annotations as-is
 * explained in <a href="https://github.com/mkarneim/pojobuilder">mkarneim's
 * Github Page</a>.
 * 
 * @author vemurthy
 */
@GeneratePojoBuilder(withName = "*Builder", withBuilderInterface = Builder.class, 
                     withBuilderProperties = true, withGenerationGap = false)
@javax.annotation.concurrent.Immutable
// class-level annotation from JSR-305
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface AnnBuilder {

}

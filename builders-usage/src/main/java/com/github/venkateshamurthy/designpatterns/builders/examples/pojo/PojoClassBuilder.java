
package com.github.venkateshamurthy.designpatterns.builders.examples.pojo;

import java.util.Date;
import java.util.List;
import com.fluentinterface.builder.Builder;
import com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClass;

public interface PojoClassBuilder
    extends Builder<PojoClass>
{


    public com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClassBuilder setA(int arg0);

    public com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClassBuilder setB(String arg0);

    public com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClassBuilder setToList(List arg0);

    public com.github.venkateshamurthy.designpatterns.builders.examples.pojo.PojoClassBuilder setJunk123(Date arg0);

}

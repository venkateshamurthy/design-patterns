package com.github.venkateshamurthy.designpatterns.builders.examples.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * A Demonstration example pojo that could be used to show builder interface generation.
 * @author vemurthy
 */
public class PojoClass {
    int a;
    String b;
    List<String> toList=new ArrayList<>();
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
    public List<String> getToList() {
        return toList;
    }
    public void setToList(List<String> toList) {
        this.toList = toList;
    }
    
    public void setJunk123(Date junk123){
        
    }
    public String toString() {
        return getClass().getSimpleName() + ":" + "a=" + a + " b=" + b+" list="+toList.toString();
    }

    public boolean equals(Object o) {
        if (o != null) {
            PojoClass that = (PojoClass) o;
            return that.a == a && (b != null && b.equals(that.b)) && toList.equals(that.toList);
        }
        return false;
    }
    
}

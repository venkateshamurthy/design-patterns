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

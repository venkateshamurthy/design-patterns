package com.github.venkateshamurthy.designpatterns.builders.pojobuilder;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class PojoClassBuilder
    implements Builder<PojoClass>, Cloneable {
  protected PojoClassBuilder self;
  protected String value$name$java$lang$String;
  protected boolean isSet$name$java$lang$String;
  protected Builder<String> builder$name$java$lang$String;
  protected String value$address$java$lang$String;
  protected boolean isSet$address$java$lang$String;
  protected Builder<String> builder$address$java$lang$String;
  protected String value$company$java$lang$String;
  protected boolean isSet$company$java$lang$String;
  protected Builder<String> builder$company$java$lang$String;

  /**
   * Creates a new {@link PojoClassBuilder}.
   */
  public PojoClassBuilder() {
    self = (PojoClassBuilder)this;
  }

  /**
   * Sets the default value for the {@link PojoClass#name} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PojoClassBuilder withName(String value) {
    this.value$name$java$lang$String = value;
    this.isSet$name$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default builder for the {@link PojoClass#name} property.
   *
   * @param builder the default builder
   * @return this builder
   */
  public PojoClassBuilder withName(Builder<String> builder) {
    this.builder$name$java$lang$String = builder;
    this.isSet$name$java$lang$String = false;
    return self;
  }

  /**
   * Sets the default value for the {@link PojoClass#address} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PojoClassBuilder withAddress(String value) {
    this.value$address$java$lang$String = value;
    this.isSet$address$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default builder for the {@link PojoClass#address} property.
   *
   * @param builder the default builder
   * @return this builder
   */
  public PojoClassBuilder withAddress(Builder<String> builder) {
    this.builder$address$java$lang$String = builder;
    this.isSet$address$java$lang$String = false;
    return self;
  }

  /**
   * Sets the default value for the {@link PojoClass#company} property.
   *
   * @param value the default value
   * @return this builder
   */
  public PojoClassBuilder withCompany(String value) {
    this.value$company$java$lang$String = value;
    this.isSet$company$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default builder for the {@link PojoClass#company} property.
   *
   * @param builder the default builder
   * @return this builder
   */
  public PojoClassBuilder withCompany(Builder<String> builder) {
    this.builder$company$java$lang$String = builder;
    this.isSet$company$java$lang$String = false;
    return self;
  }

  /**
   * Returns a clone of this builder.
   *
   * @return the clone
   */
  @Override
  public Object clone() {
    try {
      PojoClassBuilder result = (PojoClassBuilder)super.clone();
      result.self = result;
      return result;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.getMessage());
    }
  }

  /**
   * Returns a clone of this builder.
   *
   * @return the clone
   */
  public PojoClassBuilder but() {
    return (PojoClassBuilder)clone();
  }

  /**
   * Creates a new {@link PojoClass} based on this builder's settings.
   *
   * @return the created PojoClass
   */
  @Override
  public PojoClass build() {
    try {
      PojoClass result = new PojoClass();
      if (isSet$name$java$lang$String) {
        result.setName(value$name$java$lang$String);
      } else if (builder$name$java$lang$String!=null) {
        result.setName(builder$name$java$lang$String.build());
      }
      if (isSet$address$java$lang$String) {
        result.setAddress(value$address$java$lang$String);
      } else if (builder$address$java$lang$String!=null) {
        result.setAddress(builder$address$java$lang$String.build());
      }
      if (isSet$company$java$lang$String) {
        result.setCompany(value$company$java$lang$String);
      } else if (builder$company$java$lang$String!=null) {
        result.setCompany(builder$company$java$lang$String.build());
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}

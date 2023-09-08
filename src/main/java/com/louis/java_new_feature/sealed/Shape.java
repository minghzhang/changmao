package com.louis.java_new_feature.sealed;


/**
 * @date : 2021/11/25
 */
public abstract sealed class Shape permits Circle, Square {

  public final String id;

  public Shape(String id) {
    this.id = id;
  }

  public abstract double area();
}

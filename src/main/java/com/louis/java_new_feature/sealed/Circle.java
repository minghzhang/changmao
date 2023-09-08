package com.louis.java_new_feature.sealed;

/**
 * @date : 2021/11/25
 */
public non-sealed class Circle extends Shape {

  public Circle(String id) {
    super(id);
  }

  @Override
  public double area() {
    return 0;
  }
}

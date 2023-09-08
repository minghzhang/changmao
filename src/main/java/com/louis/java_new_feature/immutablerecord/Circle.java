package com.louis.java_new_feature.immutablerecord;

/**
 * @date : 2021/11/24
 */
public record Circle(double radius) implements Shape {

  public Circle {
    if (radius < 0) {
      throw new IllegalArgumentException("The radius of a circle cannot be negative [" + radius + "]");
    }
  }

  @Override
  public double area() {
    return Math.PI * radius * radius;
  }

}

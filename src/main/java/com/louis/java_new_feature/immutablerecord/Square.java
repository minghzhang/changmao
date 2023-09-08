package com.louis.java_new_feature.immutablerecord;

/**
 * @date : 2021/11/24
 */
public record Square(double side) implements Shape {

  @Override
  public double area() {
    return side * side;
  }
}

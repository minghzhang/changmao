package com.louis.java_new_feature.immutablerecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @date : 2021/11/24
 */
public class CircleTest {

  @Test
  public void area() {
    Circle circle = new Circle(10.0);
    double radius = circle.radius();
    System.out.println(radius);
    System.out.println(circle.area());

    Circle circle1 = new Circle(10.0);
    Assert.assertTrue(circle.equals(circle1));
  }


}
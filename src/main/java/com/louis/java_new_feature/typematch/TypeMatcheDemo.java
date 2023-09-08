package com.louis.java_new_feature.typematch;

import com.louis.java_new_feature.sealed.Shape;
import com.louis.java_new_feature.sealed.Square;

/**
 * @Date:12/01/2021 20:04
 * @Description:
 */
public class TypeMatcheDemo {

  public static void main(String[] args) {
    Shape shape = null;
    if (shape instanceof Square rect) {
      System.out.println(rect.id);
    }
  }

}

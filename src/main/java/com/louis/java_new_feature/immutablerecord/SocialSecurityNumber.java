package com.louis.java_new_feature.immutablerecord;

import java.util.Arrays;

/**
 * @date : 2021/11/24
 */
public record SocialSecurityNumber(byte[] ssn) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SocialSecurityNumber that = (SocialSecurityNumber) o;

    return Arrays.equals(ssn, that.ssn);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(ssn);
  }
}

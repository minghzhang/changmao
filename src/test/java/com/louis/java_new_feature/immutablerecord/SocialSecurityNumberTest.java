package com.louis.java_new_feature.immutablerecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @date : 2021/11/24
 */
public class SocialSecurityNumberTest {

  @Test
  public void ssn() {
    SocialSecurityNumber ssn = new SocialSecurityNumber(new byte[]{1, 2, 3, 4, 5, 6});
    SocialSecurityNumber ssn1 = new SocialSecurityNumber(new byte[]{1, 2, 3, 4, 5, 6});
    Assert.assertTrue(ssn.equals(ssn1));
  }
}
package org.wickedsource.coderadar.security.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordServiceTest {

  private PasswordService passwordService = new PasswordService(new BCryptPasswordEncoder());

  @Test
  public void hash() throws Exception {
    String encodedPassword = passwordService.hash("myPassword");
    boolean result = passwordService.verify("myPassword", encodedPassword);
    assertThat(result).isTrue();
  }
}

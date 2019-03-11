package org.wickedsource.coderadar.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Service for working with passwords, for example hashing or verification. */
@Service
public class PasswordService {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public PasswordService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Hash the password with "bcrypt".
   *
   * @param password the password to be hashed
   * @return hashed password as hexadecimal
   */
  public String hash(String password) {
    return passwordEncoder.encode(password);
  }
}

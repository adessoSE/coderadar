package io.reflectoring.coderadar.useradministration.service.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Service for working with passwords, for example hashing or verification. */
public class PasswordUtil {

  private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  /**
   * Hash the password with "bcrypt".
   *
   * @param password the password to be hashed
   * @return hashed password as hexadecimal
   */
  public static String hash(String password) {
    return passwordEncoder.encode(password);
  }
}

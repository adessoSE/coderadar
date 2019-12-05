package io.reflectoring.coderadar.useradministration;

import javax.validation.constraints.NotBlank;

public class UsernameAlreadyInUseException extends RuntimeException {
  public UsernameAlreadyInUseException(@NotBlank String username) {
    super("A user with the username " + username + " already exists!");
  }
}

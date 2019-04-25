package io.reflectoring.coderadar.core.projectadministration.port.driver.user.register;

import lombok.Value;

@Value
public class RegisterUserCommand {
  private String username;
  private String password;
}

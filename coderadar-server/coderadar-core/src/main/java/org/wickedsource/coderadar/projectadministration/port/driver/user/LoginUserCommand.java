package org.wickedsource.coderadar.projectadministration.port.driver.user;

import lombok.Value;

@Value
public class LoginUserCommand {
  private Long id;
  private String username;
  private String password;
}

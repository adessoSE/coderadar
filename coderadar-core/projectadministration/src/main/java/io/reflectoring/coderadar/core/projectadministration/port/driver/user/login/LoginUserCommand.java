package io.reflectoring.coderadar.core.projectadministration.port.driver.user.login;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class LoginUserCommand {
  @NotNull @NotEmpty private String username;
  @NotNull @NotEmpty private String password;
}

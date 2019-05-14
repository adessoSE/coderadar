package io.reflectoring.coderadar.core.projectadministration.port.driver.user.register;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RegisterUserCommand {
  @NotNull @NotEmpty private String username;
  @NotNull @NotEmpty private String password;
}

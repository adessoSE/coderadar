package io.reflectoring.coderadar.core.projectadministration.port.driver.user.password;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ChangePasswordCommand {
  @NotNull @NotEmpty private String refreshToken;
  @NotNull @NotEmpty private String newPassword;
}

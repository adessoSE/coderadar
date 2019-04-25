package io.reflectoring.coderadar.core.projectadministration.port.driver.user.password;

import lombok.Value;

@Value
public class ChangePasswordCommand {
  String refreshToken;
  String newPassword;
}

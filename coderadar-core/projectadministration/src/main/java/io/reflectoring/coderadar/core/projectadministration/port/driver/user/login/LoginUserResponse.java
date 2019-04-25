package io.reflectoring.coderadar.core.projectadministration.port.driver.user.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponse {
  private String accessToken;
  private String refreshToken;
}

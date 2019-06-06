package io.reflectoring.coderadar.projectadministration.port.driver.user.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponse {
  private String accessToken;
  private String refreshToken;
}

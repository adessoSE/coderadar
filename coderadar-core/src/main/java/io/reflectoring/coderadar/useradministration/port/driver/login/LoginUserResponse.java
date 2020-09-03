package io.reflectoring.coderadar.useradministration.port.driver.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponse {
  private String accessToken;
  private String refreshToken;
  private Long userId;
}

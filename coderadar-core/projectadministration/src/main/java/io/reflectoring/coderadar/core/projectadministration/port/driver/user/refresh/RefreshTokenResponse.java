package io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse {
  String token;
}

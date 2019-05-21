package io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh;

import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class RefreshTokenCommand {
  @NotBlank private String accessToken;
  @NotBlank private String refreshToken;
}

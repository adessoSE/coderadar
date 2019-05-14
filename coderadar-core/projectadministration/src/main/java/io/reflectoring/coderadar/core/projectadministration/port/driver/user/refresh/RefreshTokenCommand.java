package io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RefreshTokenCommand {
  @NotNull @NotEmpty private String accessToken;
  @NotNull @NotEmpty private String refreshToken;
}

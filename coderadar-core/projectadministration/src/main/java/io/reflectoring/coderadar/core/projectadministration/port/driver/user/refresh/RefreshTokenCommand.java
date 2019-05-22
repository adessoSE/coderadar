package io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenCommand {
  @NotBlank private String accessToken;
  @NotBlank private String refreshToken;
}

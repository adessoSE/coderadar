package io.reflectoring.coderadar.useradministration.port.driver.refresh;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenCommand {
  @NotBlank private String accessToken;
  @NotBlank private String refreshToken;
}

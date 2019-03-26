package org.wickedsource.coderadar.projectadministration.port.driver.user;

import lombok.Value;

@Value
public class RefreshTokenCommand {
  private String accessToken;
  private String refreshToken;
}

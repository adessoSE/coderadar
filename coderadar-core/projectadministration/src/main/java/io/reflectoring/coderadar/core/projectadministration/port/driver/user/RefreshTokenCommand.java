package io.reflectoring.coderadar.core.projectadministration.port.driver.user;

import lombok.Value;

@Value
public class RefreshTokenCommand {
    private String accessToken;
    private String refreshToken;
}

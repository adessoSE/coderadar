package org.wickedsource.coderadar.projectadministration.port.driven.user;

public interface RefreshTokenPort {
    String createAccessToken(String refreshToken);
}

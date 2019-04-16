package io.reflectoring.coderadar.core.projectadministration.port.driven.user;

public interface RefreshTokenPort {
  String createAccessToken(String refreshToken);
}

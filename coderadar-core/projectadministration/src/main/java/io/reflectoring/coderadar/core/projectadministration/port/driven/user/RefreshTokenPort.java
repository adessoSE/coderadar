package io.reflectoring.coderadar.core.projectadministration.port.driven.user;

import io.reflectoring.coderadar.core.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.core.projectadministration.domain.User;

public interface RefreshTokenPort {
  String createAccessToken(String refreshToken);
  RefreshToken findByToken(String refreshToken);

  void deleteByUser(User user);
}

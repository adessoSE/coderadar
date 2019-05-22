package io.reflectoring.coderadar.core.projectadministration.port.driven.user;

import io.reflectoring.coderadar.core.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.core.projectadministration.domain.User;

public interface RefreshTokenPort {
  RefreshToken findByToken(String refreshToken);
  void deleteByUser(User user);
  void updateRefreshToken(String oldToken, String newToken);
  void saveToken(RefreshToken refreshTokenEntity);
}

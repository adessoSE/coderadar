package io.reflectoring.coderadar.projectadministration.port.driven.user;

import io.reflectoring.coderadar.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.projectadministration.domain.User;

public interface RefreshTokenPort {
  RefreshToken findByToken(String refreshToken);

  void deleteByUser(User user);

  void updateRefreshToken(String oldToken, String newToken);

  void saveToken(RefreshToken refreshTokenEntity);
}

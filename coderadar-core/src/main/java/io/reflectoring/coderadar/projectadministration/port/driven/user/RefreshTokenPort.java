package io.reflectoring.coderadar.projectadministration.port.driven.user;

import io.reflectoring.coderadar.projectadministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.projectadministration.domain.User;

public interface RefreshTokenPort {
  RefreshToken findByToken(String refreshToken) throws RefreshTokenNotFoundException;

  void deleteByUser(User user);

  void saveToken(RefreshToken refreshTokenEntity);
}

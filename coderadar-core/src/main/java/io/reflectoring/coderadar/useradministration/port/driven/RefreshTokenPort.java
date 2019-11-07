package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.RefreshToken;
import io.reflectoring.coderadar.useradministration.domain.User;

public interface RefreshTokenPort {
  RefreshToken findByToken(String refreshToken) throws RefreshTokenNotFoundException;

  void deleteByUser(User user);

  void saveToken(RefreshToken refreshTokenEntity);
}

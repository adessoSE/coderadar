package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.domain.RefreshToken;
import io.reflectoring.coderadar.useradministration.domain.User;

public interface RefreshTokenPort {

  /**
   * @param refreshToken The token to find.
   * @return A RefreshToken object with the given token.
   */
  RefreshToken findByToken(String refreshToken);

  /**
   * Deletes a refresh token given a user.
   *
   * @param user The user whose tokens to delete.
   */
  void deleteByUser(User user);

  /**
   * Saves a new refresh token in the DB.
   *
   * @param refreshToken The token to save.
   */
  void saveToken(RefreshToken refreshToken);
}

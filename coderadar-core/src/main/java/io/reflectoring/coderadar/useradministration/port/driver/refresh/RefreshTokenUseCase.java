package io.reflectoring.coderadar.useradministration.port.driver.refresh;

public interface RefreshTokenUseCase {

  /**
   * Refreshes an access token.
   *
   * @param command The refresh token to use.
   * @return The new access token.
   */
  String refreshToken(RefreshTokenCommand command);
}

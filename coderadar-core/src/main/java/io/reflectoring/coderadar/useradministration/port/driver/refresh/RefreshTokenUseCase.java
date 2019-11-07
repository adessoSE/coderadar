package io.reflectoring.coderadar.useradministration.port.driver.refresh;

public interface RefreshTokenUseCase {
  String refreshToken(RefreshTokenCommand command);
}

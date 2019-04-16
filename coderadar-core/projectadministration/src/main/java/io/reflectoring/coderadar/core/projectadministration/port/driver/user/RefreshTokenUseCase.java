package io.reflectoring.coderadar.core.projectadministration.port.driver.user;

public interface RefreshTokenUseCase {
  String refreshToken(RefreshTokenCommand command);
}

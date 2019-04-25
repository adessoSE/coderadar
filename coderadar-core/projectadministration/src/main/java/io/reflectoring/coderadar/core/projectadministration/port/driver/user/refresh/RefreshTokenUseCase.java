package io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh;

public interface RefreshTokenUseCase {
  String refreshToken(RefreshTokenCommand command);
}

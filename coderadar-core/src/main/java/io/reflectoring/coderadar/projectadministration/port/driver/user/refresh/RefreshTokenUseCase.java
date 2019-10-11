package io.reflectoring.coderadar.projectadministration.port.driver.user.refresh;

public interface RefreshTokenUseCase {
  String refreshToken(RefreshTokenCommand command);
}

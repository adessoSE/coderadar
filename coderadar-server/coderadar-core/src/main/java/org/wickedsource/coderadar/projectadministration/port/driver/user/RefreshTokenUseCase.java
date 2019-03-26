package org.wickedsource.coderadar.projectadministration.port.driver.user;

public interface RefreshTokenUseCase {
  String refreshToken(RefreshTokenCommand command);
}

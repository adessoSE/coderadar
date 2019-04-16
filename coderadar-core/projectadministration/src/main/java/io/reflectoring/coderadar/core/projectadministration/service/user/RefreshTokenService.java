package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.RefreshTokenCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.RefreshTokenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService implements RefreshTokenUseCase {

  private final RefreshTokenPort port;

  @Autowired
  public RefreshTokenService(RefreshTokenPort port) {
    this.port = port;
  }

  @Override
  public String refreshToken(RefreshTokenCommand command) {
    return port.createAccessToken(command.getRefreshToken());
  }
}

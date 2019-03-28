package org.wickedsource.coderadar.projectadministration.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import org.wickedsource.coderadar.projectadministration.port.driver.user.RefreshTokenCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.user.RefreshTokenUseCase;

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

package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("RefreshTokenService")
public class RefreshTokenService implements RefreshTokenUseCase {

  private final RefreshTokenPort port;

  @Autowired
  public RefreshTokenService(@Qualifier("RefreshTokenServiceNeo4j") RefreshTokenPort port) {
    this.port = port;
  }

  @Override
  public String refreshToken(RefreshTokenCommand command) {
    return port.createAccessToken(command.getRefreshToken());
  }
}

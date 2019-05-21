package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for change password of a user. */
@Service
public class ChangePasswordService implements ChangePasswordUseCase {

  private final PasswordService passwordService;

  private final RefreshTokenPort refreshTokenPort;

  private final TokenRefreshService tokenRefreshService;

  private final RegisterUserPort registerUserPort;

  @Autowired
  public ChangePasswordService(
          PasswordService passwordService,
          RefreshTokenPort refreshTokenPort,
          TokenRefreshService tokenRefreshService, RegisterUserPort registerUserPort) {
    this.passwordService = passwordService;
    this.refreshTokenPort = refreshTokenPort;
    this.tokenRefreshService = tokenRefreshService;
    this.registerUserPort = registerUserPort;
  }

  @Override
  public void changePassword(ChangePasswordCommand command) {
    tokenRefreshService.checkUser(command.getRefreshToken());
    User user = tokenRefreshService.getUser(command.getRefreshToken());
    String hashedPassword = passwordService.hash(command.getNewPassword());
    user.setPassword(hashedPassword);
    registerUserPort.register(user);
    refreshTokenPort.deleteByUser(user);
  }
}

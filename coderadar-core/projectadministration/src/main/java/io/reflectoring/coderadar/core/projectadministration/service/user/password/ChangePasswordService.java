package io.reflectoring.coderadar.core.projectadministration.service.user.password;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.ChangePasswordPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordUseCase;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.PasswordUtil;
import io.reflectoring.coderadar.core.projectadministration.service.user.refresh.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for change password of a user. */
@Service
public class ChangePasswordService implements ChangePasswordUseCase {

  private final RefreshTokenPort refreshTokenPort;

  private final RefreshTokenService refreshTokenService;

  private final ChangePasswordPort changePasswordPort;

  @Autowired
  public ChangePasswordService(
          RefreshTokenPort refreshTokenPort,
          RefreshTokenService refreshTokenService,
          ChangePasswordPort changePasswordPort) {

    this.refreshTokenPort = refreshTokenPort;
    this.refreshTokenService = refreshTokenService;
    this.changePasswordPort = changePasswordPort;
  }

  @Override
  public void changePassword(ChangePasswordCommand command) {
    refreshTokenService.checkUser(command.getRefreshToken());
    User user = refreshTokenService.getUser(command.getRefreshToken());
    String hashedPassword = PasswordUtil.hash(command.getNewPassword());
    user.setPassword(hashedPassword);
    changePasswordPort.changePassword(user);
    refreshTokenPort.deleteByUser(user);
  }
}

package io.reflectoring.coderadar.useradministration.service.password;

import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.ChangePasswordPort;
import io.reflectoring.coderadar.useradministration.port.driven.RefreshTokenPort;
import io.reflectoring.coderadar.useradministration.port.driver.password.ChangePasswordCommand;
import io.reflectoring.coderadar.useradministration.port.driver.password.ChangePasswordUseCase;
import io.reflectoring.coderadar.useradministration.service.refresh.RefreshTokenService;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
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

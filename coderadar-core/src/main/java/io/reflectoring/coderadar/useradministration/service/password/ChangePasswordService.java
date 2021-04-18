package io.reflectoring.coderadar.useradministration.service.password;

import io.reflectoring.coderadar.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.ChangePasswordPort;
import io.reflectoring.coderadar.useradministration.port.driven.RefreshTokenPort;
import io.reflectoring.coderadar.useradministration.port.driver.password.ChangePasswordCommand;
import io.reflectoring.coderadar.useradministration.port.driver.password.ChangePasswordUseCase;
import io.reflectoring.coderadar.useradministration.service.refresh.RefreshTokenService;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service for changing the password of a user. */
@Service
@RequiredArgsConstructor
public class ChangePasswordService implements ChangePasswordUseCase {

  private final RefreshTokenPort refreshTokenPort;
  private final RefreshTokenService refreshTokenService;
  private final ChangePasswordPort changePasswordPort;

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

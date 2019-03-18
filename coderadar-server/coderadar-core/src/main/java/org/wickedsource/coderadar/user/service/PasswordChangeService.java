package org.wickedsource.coderadar.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.security.domain.ChangePasswordResponseResource;
import org.wickedsource.coderadar.security.domain.RefreshTokenRepository;
import org.wickedsource.coderadar.security.service.PasswordService;
import org.wickedsource.coderadar.user.domain.User;

/** Service for change password of a user. */
@Service
public class PasswordChangeService {

  private final PasswordService passwordService;

  private final RefreshTokenRepository refreshTokenRepository;

  private final TokenRefreshService tokenRefreshService;

  @Autowired
  public PasswordChangeService(
      PasswordService passwordService,
      RefreshTokenRepository refreshTokenRepository,
      TokenRefreshService tokenRefreshService) {
    this.passwordService = passwordService;
    this.refreshTokenRepository = refreshTokenRepository;
    this.tokenRefreshService = tokenRefreshService;
  }

  public ChangePasswordResponseResource change(String refreshToken, String newPassword) {
    tokenRefreshService.checkUser(refreshToken);
    User user = tokenRefreshService.getUser(refreshToken);
    String hashedPassword = passwordService.hash(newPassword);
    user.setPassword(hashedPassword);
    refreshTokenRepository.deleteByUser(user);

    return new ChangePasswordResponseResource();
  }
}

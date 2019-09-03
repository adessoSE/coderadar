package io.reflectoring.coderadar.projectadministration.service.user.login;

import io.reflectoring.coderadar.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserUseCase;
import io.reflectoring.coderadar.projectadministration.service.user.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService implements LoginUserUseCase {

  private final LoadUserPort loadUserPort;
  private final RefreshTokenPort refreshTokenPort;
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  @Autowired
  public LoginUserService(
      LoadUserPort loadUserPort,
      RefreshTokenPort refreshTokenPort,
      AuthenticationManager authenticationManager,
      TokenService tokenService) {
    this.loadUserPort = loadUserPort;
    this.refreshTokenPort = refreshTokenPort;
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @Override
  public LoginUserResponse login(LoginUserCommand command) {
    User user = loadUserPort.loadUserByUsername(command.getUsername());
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    refreshTokenPort.deleteByUser(user);
    
    String accessToken = tokenService.generateAccessToken(user.getId(), user.getUsername());
    String refreshToken = tokenService.generateRefreshToken(user.getId(), user.getUsername());
    saveRefreshToken(user, refreshToken);
    return new LoginUserResponse(accessToken, refreshToken);
  }

  /**
   * Saves the refresh token with relation to user.
   *
   * @param user User, that is logged in.
   * @param refreshToken the new refresh token.
   */
  void saveRefreshToken(User user, String refreshToken) {
    RefreshToken refreshTokenEntity = new RefreshToken();
    refreshTokenEntity.setToken(refreshToken);
    refreshTokenEntity.setUser(user);
    refreshTokenPort.saveToken(refreshTokenEntity);
  }
}

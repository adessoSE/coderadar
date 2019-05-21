package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Service for refreshing a access token, if access token is expired. */
@Service
public class TokenRefreshService {

  private final LoadUserPort loadUserPort;

  private final RefreshTokenPort refreshTokenPort;

  private final TokenService tokenService;

  @Autowired
  public TokenRefreshService(LoadUserPort loadUserPort, RefreshTokenPort refreshTokenPort, TokenService tokenService) {
    this.loadUserPort = loadUserPort;
    this.refreshTokenPort = refreshTokenPort;
    this.tokenService = tokenService;
  }

  /**
   * Checks the validity of refresh token and validity of the user, who sent the token.
   *
   * @param refreshToken the refresh token of the user to check
   * @throws RefreshTokenNotFoundException if the token was not found.
   * @throws UsernameNotFoundException if the user from the refresh token was not found.
   */
  void checkUser(String refreshToken) {
    RefreshToken refreshTokenEntity = refreshTokenPort.findByToken(refreshToken);
    if (refreshTokenEntity == null) {
      throw new RefreshTokenNotFoundException();
    }
    getUser(refreshToken);
    tokenService.verify(refreshToken);
  }

  /**
   * Creates new access token, if the refresh token and the user having token are valid.
   *
   * @param refreshToken the refresh token to be cheked
   * @return access token
   */
  public String createAccessToken(String refreshToken) {
    checkUser(refreshToken);
    String username = tokenService.getUsername(refreshToken);
    Optional<User> user = loadUserPort.loadUserByUsername(username);
    if(user.isPresent()){
      return tokenService.generateAccessToken(user.get().getId(), user.get().getUsername());
    } else {
      throw new UserNotFoundException(username);
    }
  }

  /**
   * reads the username from the refresh token and loads the user by thе username from thе data
   * base.
   *
   * @param refreshToken refresh token
   * @return User according to the refresh token
   */
  public User getUser(String refreshToken) {
    String username = tokenService.getUsername(refreshToken);
    Optional<User> user = loadUserPort.loadUserByUsername(username);
    if(user.isPresent()){
      return user.get();
    } else {
      throw new UserNotFoundException(username);
    }
  }
}

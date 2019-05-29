package io.reflectoring.coderadar.core.projectadministration.service.user.refresh;

import io.reflectoring.coderadar.core.projectadministration.AccessTokenNotExpiredException;
import io.reflectoring.coderadar.core.projectadministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenUseCase;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("RefreshTokenService")
public class RefreshTokenService implements RefreshTokenUseCase {

  private final LoadUserPort loadUserPort;

  private final RefreshTokenPort refreshTokenPort;

  private final TokenService tokenService;

  @Autowired
  public RefreshTokenService(
      LoadUserPort loadUserPort,
      @Qualifier("RefreshTokenServiceNeo4j") RefreshTokenPort refreshTokenPort,
      TokenService tokenService) {
    this.loadUserPort = loadUserPort;
    this.refreshTokenPort = refreshTokenPort;
    this.tokenService = tokenService;
  }

  @Override
  public String refreshToken(RefreshTokenCommand command) {
    if (tokenService.isExpired(command.getAccessToken())) {
      String token = createAccessToken(command.getRefreshToken());
      refreshTokenPort.updateRefreshToken(command.getRefreshToken(), token);
      return token;
    } else {
      throw new AccessTokenNotExpiredException();
    }
  }

  /**
   * Creates new access token, if the refresh token and the user having token are valid.
   *
   * @param refreshToken the refresh token to be cheked
   * @return access token
   */
  public String createAccessToken(String refreshToken) {
    User user = checkUser(refreshToken);
    String username = tokenService.getUsername(refreshToken);
    if (user != null) {
      return tokenService.generateAccessToken(user.getId(), user.getUsername());
    } else {
      throw new UserNotFoundException(username);
    }
  }

  /**
   * Checks the validity of refresh token and validity of the user, who sent the token.
   *
   * @param refreshToken the refresh token of the user to check
   * @throws RefreshTokenNotFoundException if the token was not found.
   * @throws UsernameNotFoundException if the user from the refresh token was not found.
   * @return User with the given refresh token.
   */
  public User checkUser(String refreshToken) {
    RefreshToken refreshTokenEntity = refreshTokenPort.findByToken(refreshToken);
    if (refreshTokenEntity == null) {
      throw new RefreshTokenNotFoundException();
    }
    User user = getUser(refreshToken);
    if (user == null) {
      throw new UserNotFoundException(user.getUsername());
    }
    tokenService.verify(refreshToken);
    return user;
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
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNotFoundException(username);
    }
  }
}

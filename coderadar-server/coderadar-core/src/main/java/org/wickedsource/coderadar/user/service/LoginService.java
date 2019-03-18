package org.wickedsource.coderadar.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.security.domain.InitializeTokenResource;
import org.wickedsource.coderadar.security.domain.RefreshToken;
import org.wickedsource.coderadar.security.domain.RefreshTokenRepository;
import org.wickedsource.coderadar.security.service.TokenService;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserRepository;

@Service
public class LoginService {

  private final UserRepository userRepository;

  private final RefreshTokenRepository refreshTokenRepository;

  private final TokenService tokenService;

  @Autowired
  public LoginService(
      UserRepository userRepository,
      RefreshTokenRepository refreshTokenRepository,
      TokenService tokenService) {
    this.userRepository = userRepository;
    this.refreshTokenRepository = refreshTokenRepository;
    this.tokenService = tokenService;
  }

  /**
   * Creates access token and refresh token and returns the both token in {@link
   * InitializeTokenResource}. The refresh token is saved in the data base with relation to user
   * with <code>username</code>.
   *
   * @param username username of teh user, who gets the token
   * @return InitializeTokenResource containing access token and refresh token.
   */
  public InitializeTokenResource login(String username) {
    User user = userRepository.findByUsername(username);
    String accessToken = tokenService.generateAccessToken(user.getId(), user.getUsername());
    String refreshToken = tokenService.generateRefreshToken(user.getId(), user.getUsername());
    saveRefreshToken(user, refreshToken);
    return new InitializeTokenResource(accessToken, refreshToken);
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
    refreshTokenRepository.save(refreshTokenEntity);
  }
}

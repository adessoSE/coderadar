package io.reflectoring.coderadar.rest.integration.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.reflectoring.coderadar.core.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.PasswordUtil;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.SecretKeyService;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.TokenService;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.TokenType;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RegisterUserRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.util.Date;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class RefreshTokenControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private RegisterUserRepository registerUserRepository;

  @Autowired private RefreshTokenRepository refreshTokenRepository;

  @Autowired private TokenService tokenService;

  @Autowired private SecretKeyService secretKeyService;

  @Test
  void refreshTokenSuccessfully() throws Exception {
    User testUser = new User();
    testUser.setUsername("radar");
    testUser.setPassword(PasswordUtil.hash("Password12!"));
    testUser = registerUserRepository.save(testUser);

    RefreshToken userRefreshToken = new RefreshToken();
    userRefreshToken.setToken(
        tokenService.generateRefreshToken(testUser.getId(), testUser.getUsername()));
    userRefreshToken.setUser(testUser);
    refreshTokenRepository.save(userRefreshToken);

    RefreshTokenCommand command =
        new RefreshTokenCommand(createExpiredAccessToken(), userRefreshToken.getToken());

    mvc()
        .perform(
            post("/user/refresh").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("token").exists());
  }

  @Test
  void refreshTokenReturnsErrorOnInvalidToken() throws Exception {
    User testUser = new User();
    testUser.setUsername("radar");
    testUser.setPassword(PasswordUtil.hash("Password12!"));
    testUser = registerUserRepository.save(testUser);

    RefreshTokenCommand command =
        new RefreshTokenCommand(createExpiredAccessToken(), "iqupiugapsfw");

    mvc()
        .perform(
            post("/user/refresh").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  void refreshTokenReturnsErrorOnNonExpiredAccessToken() throws Exception {
    User testUser = new User();
    testUser.setUsername("radar");
    testUser.setPassword(PasswordUtil.hash("Password12!"));
    testUser = registerUserRepository.save(testUser);

    RefreshToken userRefreshToken = new RefreshToken();
    userRefreshToken.setToken(
        tokenService.generateRefreshToken(testUser.getId(), testUser.getUsername()));
    userRefreshToken.setUser(testUser);
    refreshTokenRepository.save(userRefreshToken);

    RefreshTokenCommand command =
        new RefreshTokenCommand(
            tokenService.generateAccessToken(testUser.getId(), testUser.getUsername()),
            userRefreshToken.getToken());

    mvc()
        .perform(
            post("/user/refresh").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  private String createExpiredAccessToken() {
    byte[] secret = secretKeyService.getSecretKey().getEncoded();

    Date expireAt = DateTime.now().minusMinutes(14).toDate();
    Date issuedAt = DateTime.now().minusMinutes(29).toDate();
    return JWT.create()
        .withExpiresAt(expireAt)
        .withIssuedAt(issuedAt)
        .withIssuer("coderadar")
        .withClaim("userId", 1)
        .withClaim("username", "radar")
        .withClaim("type", TokenType.ACCESS.toString())
        .sign(Algorithm.HMAC256(secret));
  }
}

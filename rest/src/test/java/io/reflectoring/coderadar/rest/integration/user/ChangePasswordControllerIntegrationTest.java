package io.reflectoring.coderadar.rest.integration.user;

import io.reflectoring.coderadar.core.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.PasswordUtil;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.TokenService;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RegisterUserRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class ChangePasswordControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private RegisterUserRepository registerUserRepository;

  @Autowired private RefreshTokenRepository refreshTokenRepository;

  @Autowired private TokenService tokenService;

  @Test
  void ChangePasswordSuccessfully() throws Exception {
    User testUser = new User();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    testUser = registerUserRepository.save(testUser);

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(
        tokenService.generateRefreshToken(testUser.getId(), testUser.getUsername()));
    refreshToken.setUser(testUser);
    refreshTokenRepository.save(refreshToken);

    ChangePasswordCommand command =
        new ChangePasswordCommand(refreshToken.getToken(), "newPassword1");
    mvc()
        .perform(
            post("/user/password/change")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

    Assertions.assertTrue(
        new BCryptPasswordEncoder()
            .matches(
                "newPassword1",
                registerUserRepository.findById(testUser.getId()).get().getPassword()));
  }

  @Test
  void ChangePasswordReturnsErrorWhenTokenInvalid() throws Exception {
    User testUser = new User();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    testUser = registerUserRepository.save(testUser);

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(
        tokenService.generateRefreshToken(testUser.getId(), testUser.getUsername()));
    refreshToken.setUser(testUser);
    refreshTokenRepository.save(refreshToken);

    ChangePasswordCommand command = new ChangePasswordCommand("a", "newPassword1");
    mvc()
        .perform(
            post("/user/password/change")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    Assertions.assertTrue(
        new BCryptPasswordEncoder()
            .matches(
                "password1",
                registerUserRepository.findById(testUser.getId()).get().getPassword()));
  }
}

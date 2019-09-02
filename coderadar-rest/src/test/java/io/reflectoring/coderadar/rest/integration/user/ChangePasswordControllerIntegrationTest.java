package io.reflectoring.coderadar.rest.integration.user;

import io.reflectoring.coderadar.graph.projectadministration.domain.RefreshTokenEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.UserRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.projectadministration.service.user.security.PasswordUtil;
import io.reflectoring.coderadar.projectadministration.service.user.security.TokenService;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class ChangePasswordControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Autowired private RefreshTokenRepository refreshTokenRepository;

  @Autowired private TokenService tokenService;

  @Test
  void ChangePasswordSuccessfully() throws Exception {
    UserEntity testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    testUser = userRepository.save(testUser);

    RefreshTokenEntity refreshToken = new RefreshTokenEntity();
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
        .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(documentPasswordChange());

    Assertions.assertTrue(
        new BCryptPasswordEncoder()
            .matches(
                "newPassword1",
                    userRepository.findById(testUser.getId()).get().getPassword()));
  }

  @Test
  void ChangePasswordReturnsErrorWhenTokenInvalid() throws Exception {
    UserEntity testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    testUser = userRepository.save(testUser);

    RefreshTokenEntity refreshToken = new RefreshTokenEntity();
    refreshToken.setToken(tokenService.generateRefreshToken(testUser.getId(), testUser.getUsername()));
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
                    userRepository.findById(testUser.getId()).get().getPassword()));
  }

  private ResultHandler documentPasswordChange() {
    ConstrainedFields fields = fields(ChangePasswordCommand.class);
    return document(
            "user/password/change",
            requestFields(
                    fields.withPath("refreshToken").description("the current refresh token of the user"),
                    fields
                            .withCustomPath("newPassword")
                            .description("The password of the user as plaintext")));
  }
}

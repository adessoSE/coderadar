package io.reflectoring.coderadar.rest.integration.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.PasswordService;
import io.reflectoring.coderadar.core.projectadministration.service.user.RefreshTokenService;
import io.reflectoring.coderadar.core.projectadministration.service.user.TokenService;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RegisterUserRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ChangePasswordControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired
  private RegisterUserRepository registerUserRepository;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private PasswordService passwordService;


  @Test
  void LoginAndChangePasswordSuccessfully() throws Exception {
    registerUserRepository.deleteAll();
    User testUser = new User();
    testUser.setUsername("username");
    testUser.setPassword(passwordService.hash("password1"));
    testUser = registerUserRepository.save(testUser);

    System.out.println(passwordService.hash("password1"));

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(tokenService.generateRefreshToken(testUser.getId(), testUser.getUsername()));
    refreshToken.setUser(testUser);
    refreshTokenRepository.save(refreshToken);

    ChangePasswordCommand command = new ChangePasswordCommand(refreshToken.getToken(), "newPassword1");
    mvc().perform(post("/user/password/change").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

/*    Assertions.assertEquals(registerUserRepository.findById(testUser.getId()).get().getPassword(),
            passwordService.hash("newPassword1"));*/
  }
}

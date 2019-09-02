package io.reflectoring.coderadar.rest.integration.user;

import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.UserRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.projectadministration.service.user.security.PasswordUtil;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.any;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class LoginUserControllerIntegrationTest extends ControllerTestTemplate {
  @Autowired private UserRepository userRepository;

  @Test
  void loginUserSuccessfully() throws Exception {
    userRepository.deleteAll();
    UserEntity testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);

    LoginUserCommand command = new LoginUserCommand("username", "password1");
    mvc()
        .perform(
            post("/user/auth").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("accessToken").value(any(String.class)))
        .andExpect(MockMvcResultMatchers.jsonPath("refreshToken").value(any(String.class)))
            .andDo(documentLogin());
  }

  @Test
  void loginUserReturnsErrorWhenRequestIsInvalid() throws Exception {
    LoginUserCommand command = new LoginUserCommand("username", "pass");
    mvc()
        .perform(
            post("/user/auth").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void loginUserReturnsErrorWhenUserDoesNotExist() throws Exception {
    LoginUserCommand command = new LoginUserCommand("username", "password");
    mvc()
        .perform(
            post("/user/auth").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void loginUserReturnsErrorWhenPasswordIsWrong() throws Exception {
    UserEntity testUser = new UserEntity();
    testUser.setUsername("username2");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);

    // Test
    LoginUserCommand command = new LoginUserCommand("username2", "password3");
    mvc()
        .perform(
            post("/user/auth").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("errorMessage").value("Bad credentials"))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  private ResultHandler documentLogin() {
    ConstrainedFields fields = fields(LoginUserCommand.class);
    return document(
            "user/auth",
            requestFields(
                    fields.withPath("username").description("The name of the user to be logged in."),
                    fields.withPath("password").description("The password of the user as plaintext")));
  }
}

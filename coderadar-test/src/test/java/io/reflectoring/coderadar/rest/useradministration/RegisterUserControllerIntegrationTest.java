package io.reflectoring.coderadar.rest.useradministration;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class RegisterUserControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Test
  void registerNewUserSuccessfully() throws Exception {
    RegisterUserCommand command = new RegisterUserCommand("username", "password1");
    mvc()
        .perform(
            post("/api/user/registration")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(documentRegistration());
  }

  @Test
  void registerNewUserReturnErrorOnInvalidRequest1() throws Exception {
    RegisterUserCommand command = new RegisterUserCommand("", null);
    mvc()
        .perform(
            post("/api/user/registration")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void registerNewUserReturnErrorOnInvalidRequest2() throws Exception {
    RegisterUserCommand command = new RegisterUserCommand("username", "short");
    mvc()
        .perform(
            post("/api/user/registration")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void registerNewUserReturnErrorWhenUsernameExists() throws Exception {
    UserEntity testUser = new UserEntity();
    testUser.setUsername("username2");
    testUser.setPassword("password1");
    userRepository.save(testUser);

    // Test
    RegisterUserCommand command = new RegisterUserCommand("username2", "password1");
    mvc()
        .perform(
            post("/api/user/registration")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

  private ResultHandler documentRegistration() {
    ConstrainedFields fields = fields(RegisterUserCommand.class);
    return document(
        "user/registration",
        requestFields(
            fields.withPath("username").description("The name of the user to be registered."),
            fields
                .withCustomPath("password")
                .description("The password of the user as plaintext")));
  }
}

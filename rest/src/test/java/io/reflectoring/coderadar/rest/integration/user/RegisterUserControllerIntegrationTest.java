package io.reflectoring.coderadar.rest.integration.user;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.graph.projectadministration.user.repository.RegisterUserRepository;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class RegisterUserControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private RegisterUserRepository registerUserRepository;

  @Test
  void registerNewUserSuccessfully() throws Exception {
    RegisterUserCommand command = new RegisterUserCommand("username", "password1");
    mvc()
        .perform(
            post("/user/registration")
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
            post("/user/registration")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void registerNewUserReturnErrorOnInvalidRequest2() throws Exception {
    RegisterUserCommand command = new RegisterUserCommand("username", "short");
    mvc()
        .perform(
            post("/user/registration")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void registerNewUserReturnErrorWhenUsernameExists() throws Exception {
    User testUser = new User();
    testUser.setUsername("username2");
    testUser.setPassword("password1");
    registerUserRepository.save(testUser);

    // Test
    RegisterUserCommand command = new RegisterUserCommand("username2", "password1");
    mvc()
        .perform(
            post("/user/registration")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
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

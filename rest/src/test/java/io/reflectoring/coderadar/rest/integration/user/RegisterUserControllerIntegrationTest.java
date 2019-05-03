package io.reflectoring.coderadar.rest.integration.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

class RegisterUserControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void registerNewUserSuccessfully() throws Exception {
    RegisterUserCommand command = new RegisterUserCommand("username", "password");
    mvc().perform(post("/registration").content(toJson(command)));
  }
}

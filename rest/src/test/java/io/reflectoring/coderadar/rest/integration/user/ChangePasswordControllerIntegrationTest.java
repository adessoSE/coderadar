package io.reflectoring.coderadar.rest.integration.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

class ChangePasswordControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void changePasswordSuccessfully() throws Exception {
    ChangePasswordCommand command = new ChangePasswordCommand("refresh token", "newPassword");
    mvc().perform(post("/user/password/change").content(toJson(command)));
  }
}

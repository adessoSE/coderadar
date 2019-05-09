package io.reflectoring.coderadar.rest.integration.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

class RefreshTokenControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void refreshTokenSuccessfully() throws Exception {
    RefreshTokenCommand command = new RefreshTokenCommand("access token", "refresh token");
    mvc().perform(post("/refresh").content(toJson(command)));
  }
}

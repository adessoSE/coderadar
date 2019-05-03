package io.reflectoring.coderadar.rest.integration.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

class LoadUserControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void loadUserWithIdOne() throws Exception {
    mvc().perform(post("/1"));
  }
}

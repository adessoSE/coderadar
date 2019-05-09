package io.reflectoring.coderadar.rest.integration.user;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class LoadUserControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void loadUserWithIdOne() throws Exception {
    mvc().perform(post("/1"));
  }
}

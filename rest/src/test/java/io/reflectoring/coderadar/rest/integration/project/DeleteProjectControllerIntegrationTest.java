package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

class DeleteProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void deleteProjectWithIdOne() throws Exception {
    mvc().perform(delete("/projects/1"));
  }
}

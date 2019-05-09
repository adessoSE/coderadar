package io.reflectoring.coderadar.rest.integration.module;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

class DeleteModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void deleteModuleWithIdOne() throws Exception {
    mvc().perform(delete("/projects/1/modules/1"));
  }
}

package io.reflectoring.coderadar.rest.integration.module;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

class ListModulesOfProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void listAllModulesOfProjectWithIdOne() throws Exception {
    mvc().perform(get("/projects/1/modules"));
  }
}

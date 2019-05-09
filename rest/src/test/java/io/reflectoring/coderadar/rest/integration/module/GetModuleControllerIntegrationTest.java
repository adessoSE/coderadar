package io.reflectoring.coderadar.rest.integration.module;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

class GetModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void getModuleWithIdOne() throws Exception {
    mvc().perform(get("/projects/1/modules/1"));
  }
}

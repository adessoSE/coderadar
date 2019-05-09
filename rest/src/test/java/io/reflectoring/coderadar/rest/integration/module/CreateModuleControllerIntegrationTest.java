package io.reflectoring.coderadar.rest.integration.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CreateModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void createModuleSuccessfully() throws Exception {
    CreateModuleCommand command = new CreateModuleCommand("module-path");
    mvc().perform(post("/projects/1/modules").content(toJson(command)));
  }
}

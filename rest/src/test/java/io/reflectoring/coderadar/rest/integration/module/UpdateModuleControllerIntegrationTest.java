package io.reflectoring.coderadar.rest.integration.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class UpdateModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void updateModuleWithIdOne() throws Exception {
    UpdateModuleCommand command = new UpdateModuleCommand("new-module-path");
    mvc().perform(post("/projects/1/modules/1").content(toJson(command)));
  }
}

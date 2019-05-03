package io.reflectoring.coderadar.rest.integration.module;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

public class UpdateModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  public void updateModuleWithIdOne() throws Exception {
    UpdateModuleCommand command = new UpdateModuleCommand("new-module-path");
    mvc().perform(post("/projects/1/modules/1").content(toJson(command)));
  }
}

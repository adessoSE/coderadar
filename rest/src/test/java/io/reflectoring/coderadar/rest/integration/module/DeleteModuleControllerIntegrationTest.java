package io.reflectoring.coderadar.rest.integration.module;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

public class DeleteModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  public void deleteModuleWithIdOne() throws Exception {
    mvc().perform(delete("/projects/1/modules/1"));
  }
}

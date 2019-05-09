package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CreateAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void createAnalyzerConfigurationSuccessfully() throws Exception {
    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand("analyer", true);
    mvc().perform(post("/projects/1/analyzers").content(toJson(command)));
  }
}

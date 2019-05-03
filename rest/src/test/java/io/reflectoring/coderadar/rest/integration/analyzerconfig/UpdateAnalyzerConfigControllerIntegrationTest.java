package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

class UpdateAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void updateAnalyzerConfigurationWithIdOne() throws Exception {
    UpdateAnalyzerConfigurationCommand command =
        new UpdateAnalyzerConfigurationCommand("new analyzer name", false);
    mvc().perform(post("/projects/1/analyzers/1").content(toJson(command)));
  }
}

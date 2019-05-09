package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

class DeleteAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void deleteAnalyzerConfigurationWithIdOne() throws Exception {
    mvc().perform(delete("/projects/1/analyzers/1"));
  }
}

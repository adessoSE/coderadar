package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

class GetAnalyzerConfigsFromProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void getAllAnalyzerConfigurationsFromProjectWithIdOne() throws Exception {
    mvc().perform(get("/projects/1/analyzers"));
  }
}

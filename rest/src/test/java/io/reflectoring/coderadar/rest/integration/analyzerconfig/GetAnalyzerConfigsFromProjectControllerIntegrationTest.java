package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class GetAnalyzerConfigsFromProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void getAllAnalyzerConfigurationsFromProjectWithIdOne() throws Exception {
    mvc().perform(get("/projects/1/analyzers"));
  }
}

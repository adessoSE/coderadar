package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class GetAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void getAnalyzerConfigurationWithIdOne() throws Exception {
    mvc().perform(get("/projects/1/analyzers/1"));
  }
}

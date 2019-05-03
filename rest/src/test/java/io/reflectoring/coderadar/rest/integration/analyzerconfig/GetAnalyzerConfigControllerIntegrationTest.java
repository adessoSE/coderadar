package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

public class GetAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  public void getAnalyzerConfigurationWithIdOne() throws Exception {
    mvc().perform(get("/projects/1/analyzers/1"));
  }
}

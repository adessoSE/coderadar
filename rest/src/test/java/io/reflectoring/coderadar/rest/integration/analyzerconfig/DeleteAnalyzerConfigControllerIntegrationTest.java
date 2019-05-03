package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

public class DeleteAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  public void deleteAnalyzerConfigurationWithIdOne() throws Exception {
    mvc().perform(delete("/projects/1/analyzers/1"));
  }
}

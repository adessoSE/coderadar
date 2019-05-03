package io.reflectoring.coderadar.rest.integration.filepattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

public class ListFilePatternsOfProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  public void listAllFilePatternsOfProjectWithIdOne() throws Exception {
    mvc().perform(get("/projects/1/filePatterns"));
  }
}

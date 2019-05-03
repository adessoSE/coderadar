package io.reflectoring.coderadar.rest.integration.filepattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

public class GetFilePatternControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  public void getFilePatternWithIdOne() throws Exception {
    mvc().perform(get("/projects/1/filePatterns/1"));
  }
}

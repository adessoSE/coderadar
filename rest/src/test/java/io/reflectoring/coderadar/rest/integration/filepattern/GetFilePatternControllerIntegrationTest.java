package io.reflectoring.coderadar.rest.integration.filepattern;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class GetFilePatternControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void getFilePatternWithIdOne() throws Exception {
    mvc().perform(get("/projects/1/filePatterns/1"));
  }
}

package io.reflectoring.coderadar.rest.integration.filepattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

class UpdateFilePatternControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void updateFilePatternWithIdOne() throws Exception {
    UpdateFilePatternCommand command =
        new UpdateFilePatternCommand("**/*.java", InclusionType.EXCLUDE);
    mvc().perform(post("/projects/1/filePatterns/1").content(toJson(command)));
  }
}

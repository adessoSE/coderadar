package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class UpdateProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void updateProjectWithIdOne() throws Exception {
    URL url = new URL("http://valid.url");
    UpdateProjectCommand command =
        new UpdateProjectCommand("name", "username", "password", url, true, new Date(), new Date());
    mvc().perform(post("/projects/1").content(toJson(command)));
  }
}

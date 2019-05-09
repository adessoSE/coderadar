package io.reflectoring.coderadar.rest.integration.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.net.URL;
import java.util.Date;
import org.junit.jupiter.api.Test;

class CreateProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void createProjectSuccessfully() throws Exception {
    URL url = new URL("http://valid.url");
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", url, true, new Date(), new Date());
    mvc().perform(post("/projects").content(toJson(command)));
  }
}

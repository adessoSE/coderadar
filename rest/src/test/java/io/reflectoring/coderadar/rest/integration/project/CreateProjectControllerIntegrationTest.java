package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

package io.reflectoring.coderadar.rest.integration.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CreateProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void createProjectSuccessfully() throws Exception {
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "https://valid.url", true, new Date(), new Date());
    mvc()
        .perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void createProjectReturnsErrorOnInvalidData() throws Exception {
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "invalid", true, new Date(), new Date());
    mvc()
        .perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

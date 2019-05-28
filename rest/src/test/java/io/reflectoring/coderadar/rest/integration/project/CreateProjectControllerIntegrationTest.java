package io.reflectoring.coderadar.rest.integration.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.io.File;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CreateProjectControllerIntegrationTest extends ControllerTestTemplate {
  @Autowired private CreateProjectRepository createProjectRepository;

  @Test
  void createProjectSuccessfully() throws Exception {
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "https://valid.url", true, new Date(), new Date());
    mvc()
        .perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(
            result -> {
              FileUtils.deleteDirectory(new File("coderadar-workdir"));
            });
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

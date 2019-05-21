package io.reflectoring.coderadar.rest.integration.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class UpdateProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Test
  void updateProjectWithId() throws Exception {
    // Set up
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsEnd(new Date());
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");

    testProject = createProjectRepository.save(testProject);

    // Test
    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "name", "username", "password", "http://valid.url", true, new Date(), new Date());
    mvc()
        .perform(
            post("/projects/" + testProject.getId())
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void updateProjectReturnsErrorWhenProjectDoesNotExist() throws Exception {
    createProjectRepository.deleteAll();

    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "name", "username", "password", "http://valid.url", true, new Date(), new Date());
    mvc()
        .perform(
            post("/projects/1").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("Project with id 1 not found."));
  }

  @Test
  void updateProjectReturnsErrorWhenRequestIsInvalid() throws Exception {
    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "", "username", "password", "http://valid.url", true, new Date(), new Date());
    mvc()
        .perform(
            post("/projects/0").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

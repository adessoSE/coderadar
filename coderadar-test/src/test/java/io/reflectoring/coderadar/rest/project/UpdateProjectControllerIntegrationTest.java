package io.reflectoring.coderadar.rest.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class UpdateProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Test
  void updateProjectWithId() throws Exception {
    // Set up
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsStart(new Date());
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    testProject.setWorkdirName(UUID.randomUUID().toString());
    testProject = projectRepository.save(testProject);
    final Long id = testProject.getId();

    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "new-project-name", "username", "password", "http://valid.url", true, new Date());
    mvc()
        .perform(
            post("/api/projects/" + testProject.getId())
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            result -> {
              ProjectEntity project = projectRepository.findById(id).get();
              Assertions.assertEquals("new-project-name", project.getName());
              Assertions.assertEquals("username", project.getVcsUsername());
              Assertions.assertEquals("password", project.getVcsPassword());
              Assertions.assertEquals("http://valid.url", project.getVcsUrl());
            })
        .andDo(document("projects/update"));
  }

  @Test
  void updateProjectReturnsErrorWhenProjectDoesNotExist() throws Exception {
    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "name", "username", "password", "http://valid.url", true, new Date());
    mvc()
        .perform(
            post("/api/projects/1")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }

  @Test
  void updateProjectReturnsErrorWhenRequestIsInvalid() throws Exception {
    UpdateProjectCommand command =
        new UpdateProjectCommand("", "username", "password", "http://valid.url", true, new Date());
    mvc()
        .perform(
            post("/api/projects/0")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}

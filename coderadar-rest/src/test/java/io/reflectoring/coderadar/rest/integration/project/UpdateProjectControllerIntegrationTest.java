package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class UpdateProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Test
  void updateProjectWithId() throws Exception {
    // Set up
      ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsEnd(new Date());
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    testProject.setWorkdirName(UUID.randomUUID().toString());
    testProject = createProjectRepository.save(testProject);
    final Long id = testProject.getId();

    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "name", "username", "password", "http://valid.url", true, new Date(), new Date());
    mvc()
        .perform(
            post("/projects/" + testProject.getId())
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            result -> {
              //FileUtils.deleteDirectory(new File("coderadar-workdir/projects"));
                ProjectEntity project = createProjectRepository.findById(id).get();
              Assertions.assertEquals("name", project.getName());
              Assertions.assertEquals("username", project.getVcsUsername());
              Assertions.assertEquals("password", project.getVcsPassword());
              Assertions.assertEquals("http://valid.url", project.getVcsUrl());
              Assertions.assertTrue(project.isVcsOnline());
            })
            .andDo(document("projects/update"));
  }

  @Test
  void updateProjectReturnsErrorWhenProjectDoesNotExist() throws Exception {
    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "name", "username", "password", "http://valid.url", true, new Date(), new Date());
    mvc()
        .perform(
            post("/projects/1").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
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

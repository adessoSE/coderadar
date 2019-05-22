package io.reflectoring.coderadar.rest.integration.module;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CreateModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @BeforeEach
  public void setUp() {}

  @Test
  void createModuleSuccessfully() throws Exception {
    // Set up
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

    // Test
    CreateModuleCommand command = new CreateModuleCommand("module-path");
    mvc()
        .perform(
            post("/projects/" + testProject.getId() + "/modules")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdqupigpigu"))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void createModuleReturnsErrorWhenProjectNotFound() throws Exception {
    createProjectRepository.deleteAll();

    CreateModuleCommand command = new CreateModuleCommand("module-path");
    mvc()
        .perform(
            post("/projects/1/modules")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }

  @Test
  void createModuleReturnsErrorWhenRequestIsInvalid() throws Exception {
    CreateModuleCommand command = new CreateModuleCommand("");
    mvc()
        .perform(
            post("/projects/0/modules")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

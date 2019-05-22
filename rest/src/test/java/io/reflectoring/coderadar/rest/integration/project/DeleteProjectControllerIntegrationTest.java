package io.reflectoring.coderadar.rest.integration.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class DeleteProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Test
  void deleteProjectWithId() throws Exception {
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

    mvc()
        .perform(delete("/projects/" + testProject.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void deleteProjectReturnsErrorWhenProjectNotFound() throws Exception {
    createProjectRepository.deleteAll();

    mvc()
        .perform(delete("/projects/1"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }
}

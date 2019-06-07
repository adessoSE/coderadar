package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

class DeleteProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Test
  void deleteProjectWithId() throws Exception {
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);
    final Long id = testProject.getId();

    mvc()
        .perform(delete("/projects/" + testProject.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            result -> {
              Assertions.assertFalse(createProjectRepository.findById(id).isPresent());
            })
            .andDo(document("projects/delete"));
  }

  @Test
  void deleteProjectReturnsErrorWhenProjectNotFound() throws Exception {
    mvc()
        .perform(delete("/projects/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }
}

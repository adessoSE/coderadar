package io.reflectoring.coderadar.rest.integration.module;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class DeleteModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateModuleRepository createModuleRepository;

  @Test
  void deleteModuleWithId() throws Exception {
    // Set up
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    createProjectRepository.save(testProject);

    Module module = new Module();
    module.setPath("test-module");
    module.setProject(testProject);
    module = createModuleRepository.save(module);

    // Test
    mvc()
        .perform(delete("/projects/" + testProject.getId() + "/modules/" + module.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void deleteModuleReturnsErrorWhenModuleNotFound() throws Exception {
    createModuleRepository.deleteAll();

    mvc()
        .perform(delete("/projects/0/modules/0"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("errorMessage").value("Module with id 0 not found."));
  }
}

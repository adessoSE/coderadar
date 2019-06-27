package io.reflectoring.coderadar.rest.integration.module;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

class DeleteModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateModuleRepository createModuleRepository;

  @Test
  void deleteModuleWithId() throws Exception {
    // Set up
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    createProjectRepository.save(testProject);

    ModuleEntity module = new ModuleEntity();
    module.setPath("test-module");
    module.setProject(testProject);
    module = createModuleRepository.save(module);
    final Long id = module.getId();

    // Test
    mvc()
        .perform(delete("/projects/" + testProject.getId() + "/modules/" + module.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(result -> Assertions.assertFalse(createModuleRepository.findById(id).isPresent()))
            .andDo(document("modules/delete"));
  }

  @Test
  void deleteModuleReturnsErrorWhenModuleNotFound() throws Exception {
    mvc()
        .perform(delete("/projects/0/modules/0"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Module with id 0 not found."));
  }
}

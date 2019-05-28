package io.reflectoring.coderadar.rest.integration.module;

import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ListModulesOfProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateModuleRepository createModuleRepository;

  @Test
  void listAllModulesOfProjectWithId() throws Exception {
    // Set up
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

    Module module = new Module();
    module.setPath("test-module");
    module.setProject(testProject);
    createModuleRepository.save(module);

    Module module2 = new Module();
    module2.setPath("test-module");
    module2.setProject(testProject);
    createModuleRepository.save(module2);

    // Test
    mvc()
        .perform(get("/projects/" + testProject.getId() + "/modules"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetModuleResponse[].class));
  }

  @Test
  void listAllModulesOfProjectReturnsErrorWhenProjectNotFound() throws Exception {
    createProjectRepository.deleteAll();

    mvc()
        .perform(get("/projects/1/modules"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }
}

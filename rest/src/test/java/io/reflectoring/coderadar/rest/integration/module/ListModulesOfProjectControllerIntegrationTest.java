package io.reflectoring.coderadar.rest.integration.module;

import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ListModulesOfProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateModuleRepository createModuleRepository;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    Project testProject = new Project();
    testProject.setVcsUrl(new URL("https://valid.url"));
    createProjectRepository.save(testProject);

    Module module = new Module();
    module.setPath("test-module");
    module.setProject(testProject);
    createModuleRepository.save(module);

    Module module2 = new Module();
    module2.setPath("test-module");
    module2.setProject(testProject);
    createModuleRepository.save(module2);
  }

  @Test
  void listAllModulesOfProjectWithIdOne() throws Exception {
    mvc()
        .perform(get("/projects/0/modules"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetModuleResponse[].class));
  }

  @Test
  void listAllModulesOfProjectReturnsErrorWhenProjectNotFound() throws Exception {
    mvc()
        .perform(get("/projects/1/modules"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("Project with id 1 not found."));
  }
}

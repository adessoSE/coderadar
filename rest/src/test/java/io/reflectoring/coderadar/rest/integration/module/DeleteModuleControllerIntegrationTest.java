package io.reflectoring.coderadar.rest.integration.module;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class DeleteModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateModuleRepository createModuleRepository;

  @BeforeEach
  public void setUp() {
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    createProjectRepository.save(testProject);

    Module module = new Module();
    module.setPath("test-module");
    module.setProject(testProject);
    createModuleRepository.save(module);
  }

  @Test
  void deleteModuleWithIdOne() throws Exception {
    mvc().perform(delete("/projects/0/modules/1")).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void deleteModuleReturnsErrorWhenModuleNotFound() throws Exception {
    mvc()
        .perform(delete("/projects/0/modules/0"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("Module with id 0 not found."));
  }
}

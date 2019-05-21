package io.reflectoring.coderadar.rest.integration.module;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class UpdateModuleControllerIntegrationTest extends ControllerTestTemplate {

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
  void updateModuleWithIdOne() throws Exception {
    UpdateModuleCommand command = new UpdateModuleCommand("new-module-path");
    mvc()
        .perform(
            post("/projects/0/modules/1")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void updateModuleReturnsErrorWhenModuleNotFound() throws Exception {
    UpdateModuleCommand command = new UpdateModuleCommand("new-module-path");
    mvc()
        .perform(
            post("/projects/0/modules/2")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("Module with id 2 not found."));
  }

  @Test
  void updateModuleReturnsErrorWhenRequestIsInvalid() throws Exception {
    UpdateModuleCommand command = new UpdateModuleCommand("");
    mvc()
        .perform(
            post("/projects/0/modules/1")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

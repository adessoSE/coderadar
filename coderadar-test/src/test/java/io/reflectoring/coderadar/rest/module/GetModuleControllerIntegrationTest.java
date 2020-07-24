package io.reflectoring.coderadar.rest.module;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetModuleResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class GetModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Autowired private ModuleRepository moduleRepository;

  @Test
  void getModuleWithId() throws Exception {
    // Set up
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    ModuleEntity module = new ModuleEntity();
    module.setPath("test-module");
    module.setProject(testProject);
    module = moduleRepository.save(module);

    // Test
    mvc()
        .perform(get("/api/projects/" + testProject.getId() + "/modules/" + module.getId()))
        .andExpect(status().isOk())
        .andExpect(containsResource(GetModuleResponse.class))
        .andDo(
            result -> {
              GetModuleResponse response =
                  fromJson(result.getResponse().getContentAsString(), GetModuleResponse.class);
              Assertions.assertEquals("test-module", response.getPath());
            })
        .andDo(document("modules/get"));
  }

  @Test
  void getModuleReturnsErrorWhenModuleNotFound() throws Exception {
    mvc()
        .perform(get("/api/projects/0/modules/0"))
        .andExpect(status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Module with id 0 not found."));
  }
}

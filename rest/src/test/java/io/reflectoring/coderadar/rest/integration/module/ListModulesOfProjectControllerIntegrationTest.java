package io.reflectoring.coderadar.rest.integration.module;

import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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

    testProject.setModules(Arrays.asList(module, module2));
    testProject = createProjectRepository.save(testProject);

    // Test
    mvc()
        .perform(get("/projects/" + testProject.getId() + "/modules"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetModuleResponse[].class))
        .andExpect(containsResource(GetModuleResponse[].class))
        .andExpect(
            result -> {
              GetModuleResponse[] moduleResponses =
                  fromJson(result.getResponse().getContentAsString(), GetModuleResponse[].class);
              Assertions.assertEquals(2, moduleResponses.length);
            })
            .andDo(document("modules/list"));
  }

  @Test
  void listAllModulesOfProjectReturnsErrorWhenProjectNotFound() throws Exception {
    mvc()
        .perform(get("/projects/1/modules"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }
}

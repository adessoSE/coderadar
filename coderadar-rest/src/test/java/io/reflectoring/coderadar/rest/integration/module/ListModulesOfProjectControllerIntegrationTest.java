package io.reflectoring.coderadar.rest.integration.module;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ListModulesOfProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateModuleRepository createModuleRepository;

  @Test
  void listAllModulesOfProjectWithId() throws Exception {
    // Set up
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

    ModuleEntity module = new ModuleEntity();
    module.setPath("test-module/");
    module.setProject(testProject);
    module = createModuleRepository.save(module);

    ModuleEntity module2 = new ModuleEntity();
    module2.setPath("test-module/src");
    module2.setParentModule(module);
    module2 = createModuleRepository.save(module2);
    module.getChildModules().add(module2);
    module = createModuleRepository.save(module);

    ModuleEntity module3 = new ModuleEntity();
    module3.setPath("test-module2");
    module3.setProject(testProject);
    module3 = createModuleRepository.save(module3);

    ModuleEntity module4 = new ModuleEntity();
    module4.setPath("test-module/src/asd");
    module4.setParentModule(module2);
    module4 = createModuleRepository.save(module4);
    module2.getChildModules().add(module4);
    module2 = createModuleRepository.save(module2);

    testProject.setModules(Arrays.asList(module, module3));
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
              Assertions.assertEquals(4, moduleResponses.length);
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

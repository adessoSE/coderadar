package io.reflectoring.coderadar.rest.integration.module;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CreateModuleControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;
  @Autowired private CreateModuleRepository createModuleRepository;

  @Test
  void createModuleSuccessfully() throws Exception {
    // Set up
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

    ConstrainedFields fields = fields(CreateModuleCommand.class);

    // Test
    CreateModuleCommand command = new CreateModuleCommand("module-path");
    mvc()
        .perform(
            post("/projects/" + testProject.getId() + "/modules")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(
            result -> {
              Long id =
                  fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
              ModuleEntity module = createModuleRepository.findById(id).get();
              Assertions.assertEquals("module-path/", module.getPath());
            })
            .andDo(
                    document(
                            "modules/create",
                            requestFields(
                                    fields
                                            .withPath("path")
                                            .description(
                                                    "The path of this module starting at the VCS root. All files below that path are considered to be part of the module."))));
  }

  @Test
  void createModuleReturnsErrorWhenProjectNotFound() throws Exception {
    CreateModuleCommand command = new CreateModuleCommand("module-path");
    mvc()
        .perform(
            post("/projects/1/modules")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }

  @Test
  void createModuleReturnsErrorWhenRequestIsInvalid() throws Exception {
    CreateModuleCommand command = new CreateModuleCommand("");
    mvc()
        .perform(
            post("/projects/0/modules")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

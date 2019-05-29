package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CreateAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;
  @Autowired private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @Test
  void createAnalyzerConfigurationSuccessfully() throws Exception {
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

      ConstrainedFields<CreateAnalyzerConfigurationCommand> fields = fields(CreateAnalyzerConfigurationCommand.class);

    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand("analyzer", true);
    mvc()
        .perform(
            post("/projects/" + testProject.getId() + "/analyzers")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(containsResource(IdResponse.class))
        .andDo(
            result -> {
              Long id =
                  fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
              AnalyzerConfiguration analyzerConfiguration =
                  createAnalyzerConfigurationRepository.findById(id).get();
              Assertions.assertEquals("analyzer", analyzerConfiguration.getAnalyzerName());
              Assertions.assertTrue(analyzerConfiguration.getEnabled());
            })
            .andDo(
                    document(
                            "analyzerConfiguration/post",
                            requestFields(
                                    fields
                                            .withPath("analyzerName")
                                            .description(
                                                    "Name of the analyzer plugin to which the AnalyzerConfiguration is applied. This should always be the fully qualified class name of the class that implements the plugin interface."),
                                    fields
                                            .withPath("enabled")
                                            .description(
                                                    "Set to TRUE if you want the analyzer plugin to be enabled and to FALSE if not. You have to specify each analyzer plugin you want to have enabled. If a project does not have a configuration for a certain plugin, that plugin is NOT enabled by default."))));

  }

  @Test
  void createAnalyzerConfigurationReturnsErrorWhenProjectNotFound() throws Exception {
    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand("analyzer", true);
    mvc()
        .perform(
            post("/projects/1/analyzers")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }

  @Test
  void createAnalyzerConfigurationReturnsErrorWhenRequestIsInvalid() throws Exception {
    CreateAnalyzerConfigurationCommand command = new CreateAnalyzerConfigurationCommand("", true);
    mvc()
        .perform(
            post("/projects/1/analyzers")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

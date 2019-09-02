package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CreateAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;
  @Autowired private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  @Test
  void createAnalyzerConfigurationSuccessfully() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

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
              AnalyzerConfigurationEntity analyzerConfiguration =
                      analyzerConfigurationRepository.findById(id).get();
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
        .andExpect(MockMvcResultMatchers.status().isNotFound())
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
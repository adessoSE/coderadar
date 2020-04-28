package io.reflectoring.coderadar.rest.analyzerconfig;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CreateAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;
  @Autowired private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  @Test
  void createAnalyzerConfigurationSuccessfully() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    ConstrainedFields<CreateAnalyzerConfigurationCommand> fields =
        fields(CreateAnalyzerConfigurationCommand.class);

    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand(
            "io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin",
            true);
    mvc()
        .perform(
            post("/api/projects/" + testProject.getId() + "/analyzers")
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
              Assertions.assertEquals(
                  "io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin",
                  analyzerConfiguration.getAnalyzerName());
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
        new CreateAnalyzerConfigurationCommand(
            "io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin",
            true);
    mvc()
        .perform(
            post("/api/projects/1/analyzers")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }

  @Test
  void createAnalyzerConfigurationReturnsErrorWhenAnalyzerNotFound() throws Exception {
    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand("analyzer", true);
    mvc()
        .perform(
            post("/api/projects/1/analyzers")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("Analyzer with name analyzer not found"));
  }

  @Test
  void createAnalyzerConfigurationReturnsErrorWhenAnalyzerAlreadyConfigured() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    AnalyzerConfigurationEntity analyzerConfigurationEntity = new AnalyzerConfigurationEntity();
    analyzerConfigurationEntity.setAnalyzerName(
        "io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin");
    analyzerConfigurationEntity.setEnabled(true);
    testProject.getAnalyzerConfigurations().add(analyzerConfigurationEntity);
    testProject = projectRepository.save(testProject);

    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand(
            "io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin",
            true);
    mvc()
        .perform(
            post("/api/projects/" + testProject.getId() + "/analyzers")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isConflict())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("An analyzer with this name is already configured for the project!"));
  }

  @Test
  void createAnalyzerConfigurationReturnsErrorWhenRequestIsInvalid() throws Exception {
    CreateAnalyzerConfigurationCommand command = new CreateAnalyzerConfigurationCommand("", true);
    mvc()
        .perform(
            post("/api/projects/1/analyzers")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

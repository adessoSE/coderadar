package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class UpdateAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Autowired private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  @Test
  void updateAnalyzerConfigurationWithId() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    AnalyzerConfigurationEntity analyzerConfiguration = new AnalyzerConfigurationEntity();
    analyzerConfiguration.setProject(testProject);
    analyzerConfiguration.setAnalyzerName("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin");

    analyzerConfiguration = analyzerConfigurationRepository.save(analyzerConfiguration);
    final Long id = analyzerConfiguration.getId();

    UpdateAnalyzerConfigurationCommand command =
        new UpdateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin", false);
    mvc()
        .perform(
            post("/projects/" + testProject.getId() + "/analyzers/" + analyzerConfiguration.getId())
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            result -> {
              AnalyzerConfigurationEntity configuration =
                      analyzerConfigurationRepository.findById(id).get();
              Assertions.assertEquals("io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin", configuration.getAnalyzerName());
              Assertions.assertFalse(configuration.getEnabled());
            })
            .andDo(document("analyzerConfiguration/update"));

  }

  @Test
  void updateAnalyzerConfigurationReturnsErrorWhenAnalyzerDoesNotExist() throws Exception {
    UpdateAnalyzerConfigurationCommand command =
            new UpdateAnalyzerConfigurationCommand("noanalyzer", false);
    mvc()
            .perform(
                    post("/projects/0/analyzers/2")
                            .content(toJson(command))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(
                    MockMvcResultMatchers.jsonPath("errorMessage")
                            .value("AnalyzerConfiguration with id 2 not found."));
  }

  @Test
  void updateAnalyzerConfigurationReturnsErrorWhenAnalyzerAlreadyConfigured() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    AnalyzerConfigurationEntity analyzerConfiguration = new AnalyzerConfigurationEntity();
    analyzerConfiguration.setProject(testProject);
    analyzerConfiguration.setAnalyzerName("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin");

    analyzerConfiguration = analyzerConfigurationRepository.save(analyzerConfiguration);

    AnalyzerConfigurationEntity analyzerConfiguration2 = new AnalyzerConfigurationEntity();
    analyzerConfiguration2.setProject(testProject);
    analyzerConfiguration2.setAnalyzerName("io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin");

    analyzerConfigurationRepository.save(analyzerConfiguration2);

    final Long id = analyzerConfiguration.getId();

    UpdateAnalyzerConfigurationCommand command =
            new UpdateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin", false);
    mvc()
            .perform(
                    post("/projects/" + testProject.getId() + "/analyzers/"+id)
                            .content(toJson(command))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(
                    MockMvcResultMatchers.jsonPath("errorMessage").value("An analyzer with this name is already configured for the project!"));
  }

  @Test
  void updateAnalyzerConfigurationReturnsErrorWhenNotFound() throws Exception {
    UpdateAnalyzerConfigurationCommand command =
            new UpdateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", false);
    mvc()
            .perform(
                    post("/projects/0/analyzers/2")
                            .content(toJson(command))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(
                    MockMvcResultMatchers.jsonPath("errorMessage")
                            .value("AnalyzerConfiguration with id 2 not found."));
  }

  @Test
  void updateAnalyzerConfigurationReturnsErrorWhenRequestInvalid() throws Exception {
    UpdateAnalyzerConfigurationCommand command = new UpdateAnalyzerConfigurationCommand("", false);
    mvc()
        .perform(
            post("/projects/0/analyzers/1")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

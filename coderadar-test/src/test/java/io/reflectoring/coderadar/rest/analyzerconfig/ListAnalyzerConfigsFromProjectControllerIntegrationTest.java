package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetAnalyzerConfigurationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ListAnalyzerConfigsFromProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Autowired private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  @Test
  void listAnalyzerConfigurationsFromProject() throws Exception {
    // Set up
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    AnalyzerConfigurationEntity analyzerConfiguration = new AnalyzerConfigurationEntity();
    analyzerConfiguration.setProject(testProject);
    analyzerConfiguration.setAnalyzerName("analyzer");
    analyzerConfiguration.setEnabled(true);

    analyzerConfigurationRepository.save(analyzerConfiguration);

    AnalyzerConfigurationEntity analyzerConfiguration2 = new AnalyzerConfigurationEntity();
    analyzerConfiguration2.setProject(testProject);
    analyzerConfiguration2.setAnalyzerName("analyzer2");
    analyzerConfiguration2.setEnabled(false);

    analyzerConfigurationRepository.save(analyzerConfiguration2);

    testProject.setAnalyzerConfigurations(
        Arrays.asList(analyzerConfiguration, analyzerConfiguration2));
    testProject = projectRepository.save(testProject);

    // Test
    mvc()
        .perform(get("/projects/" + testProject.getId() + "/analyzers"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetAnalyzerConfigurationResponse[].class))
        .andExpect(
            result -> {
              GetAnalyzerConfigurationResponse[] configurationResponses =
                  fromJson(
                      result.getResponse().getContentAsString(),
                      GetAnalyzerConfigurationResponse[].class);
              Assertions.assertEquals(2, configurationResponses.length);
            })
            .andDo(document("analyzerConfiguration/get"));
  }

  @Test
  void listAnalyzerConfigurationsReturnsErrorWhenProjectNotFound() throws Exception {
    mvc()
        .perform(get("/projects/1/analyzers"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }
}

package io.reflectoring.coderadar.rest.analyzerconfig;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

class GetAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Autowired private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  @Test
  void getAnalyzerConfigurationWithId() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    AnalyzerConfigurationEntity analyzerConfiguration = new AnalyzerConfigurationEntity();
    analyzerConfiguration.setProject(testProject);
    analyzerConfiguration.setAnalyzerName("analyzer");
    analyzerConfiguration.setEnabled(true);

    analyzerConfiguration = analyzerConfigurationRepository.save(analyzerConfiguration);

    mvc()
        .perform(
            get(
                "/api/projects/"
                    + testProject.getId()
                    + "/analyzers/"
                    + analyzerConfiguration.getId()))
        .andExpect(status().isOk())
        .andExpect(containsResource(GetAnalyzerConfigurationResponse.class))
        .andDo(
            result -> {
              GetAnalyzerConfigurationResponse response =
                  fromJson(
                      result.getResponse().getContentAsString(),
                      GetAnalyzerConfigurationResponse.class);
              Assertions.assertEquals("analyzer", response.getAnalyzerName());
              Assertions.assertTrue(response.isEnabled());
            })
        .andDo(document("analyzerConfiguration/getSingle"));
  }

  @Test
  void getAnalyzerConfigurationReturnsErrorWhenNotFound() throws Exception {
    mvc()
        .perform(get("/api/projects/0/analyzers/2"))
        .andExpect(status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("AnalyzerConfiguration with id 2 not found."))
        .andDo(document("analyzerConfiguration/update"));
  }
}

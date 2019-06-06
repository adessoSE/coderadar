package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class GetAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @Test
  void getAnalyzerConfigurationWithId() throws Exception {
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setProject(testProject);
    analyzerConfiguration.setAnalyzerName("analyzer");
    analyzerConfiguration.setEnabled(true);

    analyzerConfiguration = createAnalyzerConfigurationRepository.save(analyzerConfiguration);

    mvc()
        .perform(
            get("/projects/" + testProject.getId() + "/analyzers/" + analyzerConfiguration.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetAnalyzerConfigurationResponse.class))
        .andDo(
            result -> {
              GetAnalyzerConfigurationResponse response =
                  fromJson(
                      result.getResponse().getContentAsString(),
                      GetAnalyzerConfigurationResponse.class);
              Assertions.assertEquals("analyzer", response.getAnalyzerName());
              Assertions.assertTrue(response.getEnabled());
            });
  }

  @Test
  void getAnalyzerConfigurationReturnsErrorWhenNotFound() throws Exception {
    mvc()
        .perform(get("/projects/0/analyzers/2"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("AnalyzerConfiguration with id 2 not found."));
  }
}

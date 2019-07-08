package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class GetAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @Test
  void getAnalyzerConfigurationWithId() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

    AnalyzerConfigurationEntity analyzerConfiguration = new AnalyzerConfigurationEntity();
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
            })
            .andDo(document("analyzerConfiguration/getSingle"));

  }

  @Test
  void getAnalyzerConfigurationReturnsErrorWhenNotFound() throws Exception {
    mvc()
        .perform(get("/projects/0/analyzers/2"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("AnalyzerConfiguration with id 2 not found."))
            .andDo(document("analyzerConfiguration/update"));

  }
}

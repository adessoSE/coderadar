package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class GetAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    Project testProject = new Project();
    testProject.setVcsUrl(new URL("https://valid.url"));
    createProjectRepository.save(testProject);

    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setProject(testProject);
    analyzerConfiguration.setAnalyzerName("analyzer");
    analyzerConfiguration.setEnabled(true);

    createAnalyzerConfigurationRepository.save(analyzerConfiguration);
  }

  @Test
  void getAnalyzerConfigurationWithIdOne() throws Exception {
    mvc()
        .perform(get("/projects/0/analyzers/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetAnalyzerConfigurationResponse.class));
  }

  @Test
  void getAnalyzerConfigurationReturnsErrorWhenNotFound() throws Exception {
    mvc()
        .perform(get("/projects/0/analyzers/2"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(
            MockMvcResultMatchers.content().string("AnalyzerConfiguration with id 2 not found."));
  }
}
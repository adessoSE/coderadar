package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.MalformedURLException;
import java.net.URL;

class UpdateAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired
  private CreateProjectRepository createProjectRepository;

  @Autowired
  private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    Project testProject = new Project();
    testProject.setVcsUrl(new URL("https://valid.url"));
    createProjectRepository.save(testProject);

    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setProject(testProject);
    analyzerConfiguration.setAnalyzerName("analyzer");

    createAnalyzerConfigurationRepository.save(analyzerConfiguration);
  }

  @Test
  void updateAnalyzerConfigurationWithIdOne() throws Exception {
    UpdateAnalyzerConfigurationCommand command =
        new UpdateAnalyzerConfigurationCommand("new analyzer name", false);
    mvc().perform(post("/projects/0/analyzers/1").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void updateAnalyzerConfigurationReturnsErrorWhenNotFound() throws Exception {
    UpdateAnalyzerConfigurationCommand command = new UpdateAnalyzerConfigurationCommand("new analyzer name", false);
    mvc().perform(post("/projects/0/analyzers/2").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("AnalyzerConfiguration with id 2 not found."));
  }

  @Test
  void updateAnalyzerConfigurationReturnsErrorWhenRequestInvalid() throws Exception {
    UpdateAnalyzerConfigurationCommand command = new UpdateAnalyzerConfigurationCommand("", false);
    mvc().perform(post("/projects/0/analyzers/1").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

package io.reflectoring.coderadar.rest.analyzerconfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class DeleteAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Autowired private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  @Test
  void deleteAnalyzerConfigurationWithId() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    AnalyzerConfigurationEntity analyzerConfiguration = new AnalyzerConfigurationEntity();
    analyzerConfiguration.setProject(testProject);
    analyzerConfiguration.setAnalyzerName("analyzer");
    analyzerConfiguration.setEnabled(true);

    analyzerConfiguration = analyzerConfigurationRepository.save(analyzerConfiguration);
    final Long id = analyzerConfiguration.getId();

    mvc()
        .perform(
            delete(
                "/api/projects/"
                    + testProject.getId()
                    + "/analyzers/"
                    + analyzerConfiguration.getId()))
        .andExpect(status().isOk())
        .andDo(
            result -> {
              Assertions.assertFalse(analyzerConfigurationRepository.findById(id).isPresent());
            })
        .andDo(document("analyzerConfiguration/delete"));
  }

  @Test
  void deleteAnalyzerConfigurationReturnsErrorWhenAnalyzerNotFound() throws Exception {
    mvc()
        .perform(delete("/api/projects/0/analyzers/2"))
        .andExpect(status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("AnalyzerConfiguration with id 2 not found."));
  }
}

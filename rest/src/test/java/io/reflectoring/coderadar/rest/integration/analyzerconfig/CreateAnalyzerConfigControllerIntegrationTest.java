package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CreateAnalyzerConfigControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;
  @Autowired private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @Test
  void createAnalyzerConfigurationSuccessfully() throws Exception {
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

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
              AnalyzerConfiguration analyzerConfiguration =
                  createAnalyzerConfigurationRepository.findById(id).get();
              Assertions.assertEquals("analyzer", analyzerConfiguration.getAnalyzerName());
              Assertions.assertTrue(analyzerConfiguration.getEnabled());
            });
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
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
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

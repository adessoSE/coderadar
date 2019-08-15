package io.reflectoring.coderadar.rest.integration.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CreateFilePatternControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;
  @Autowired private FilePatternRepository filePatternRepository;

  @Test
  void createFilePatternSuccessfully() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/projects/" + testProject.getId() + "/filePatterns")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(
            result -> {
              FilePatternEntity filePattern = filePatternRepository.findAll().iterator().next();
              Assertions.assertEquals("**/*.java", filePattern.getPattern());
              Assertions.assertEquals(InclusionType.INCLUDE, filePattern.getInclusionType());
            });
  }

  @Test
  void createFilePatternReturnsErrorWhenProjectDoesNotExist() throws Exception {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/projects/1/filePatterns")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }

  @Test
  void createFilePatternReturnsErrorWhenRequestIsInvalid() throws Exception {
    CreateFilePatternCommand command = new CreateFilePatternCommand("", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/projects/1/filePatterns")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

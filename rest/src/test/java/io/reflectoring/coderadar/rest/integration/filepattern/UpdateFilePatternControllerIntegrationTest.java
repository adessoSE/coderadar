package io.reflectoring.coderadar.rest.integration.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.CreateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class UpdateFilePatternControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateFilePatternRepository createFilePatternRepository;

  @Test
  void updateFilePatternWithId() throws Exception {
    // Set up
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

    FilePattern filePattern = new FilePattern();
    filePattern.setInclusionType(InclusionType.INCLUDE);
    filePattern.setPattern("**/*.java");
    filePattern.setProject(testProject);
    filePattern = createFilePatternRepository.save(filePattern);
    final Long id = filePattern.getId();

    // Test
    UpdateFilePatternCommand command =
        new UpdateFilePatternCommand("**/*.xml", InclusionType.EXCLUDE);
    mvc()
        .perform(
            post("/projects/" + testProject.getId() + "/filePatterns/" + filePattern.getId())
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            result -> {
              FilePattern configuration = createFilePatternRepository.findById(id).get();
              Assertions.assertEquals("**/*.xml", configuration.getPattern());
              Assertions.assertEquals(InclusionType.EXCLUDE, configuration.getInclusionType());
            });
  }

  @Test
  void updateFilePatternReturnsErrorWhenNotFound() throws Exception {
    UpdateFilePatternCommand command =
        new UpdateFilePatternCommand("**/*.java", InclusionType.EXCLUDE);
    mvc()
        .perform(
            post("/projects/0/filePatterns/2")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("FilePattern with id 2 not found."));
  }

  @Test
  void updateFilePatternReturnsErrorWhenRequestIsInvalid() throws Exception {
    UpdateFilePatternCommand command = new UpdateFilePatternCommand("", null);
    mvc()
        .perform(
            post("/projects/0/filePatterns/1")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

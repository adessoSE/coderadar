package io.reflectoring.coderadar.rest.integration.filepattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.CreateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class UpdateFilePatternControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateFilePatternRepository createFilePatternRepository;

  @BeforeEach
  public void setUp() {
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    createProjectRepository.save(testProject);

    FilePattern filePattern = new FilePattern();
    filePattern.setInclusionType(InclusionType.INCLUDE);
    filePattern.setPattern("**/*.java");
    filePattern.setProject(testProject);
    createFilePatternRepository.save(filePattern);
  }

  @Test
  void updateFilePatternWithIdOne() throws Exception {
    UpdateFilePatternCommand command =
        new UpdateFilePatternCommand("**/*.java", InclusionType.EXCLUDE);
    mvc()
        .perform(
            post("/projects/0/filePatterns/1")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
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
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("FilePattern with id 2 not found."));
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

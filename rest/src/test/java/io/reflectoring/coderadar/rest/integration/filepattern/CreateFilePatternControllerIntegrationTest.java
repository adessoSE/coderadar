package io.reflectoring.coderadar.rest.integration.filepattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.MalformedURLException;
import java.net.URL;

class CreateFilePatternControllerIntegrationTest extends ControllerTestTemplate {


  @Autowired
  private CreateProjectRepository createProjectRepository;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    Project testProject = new Project();
    testProject.setVcsUrl(new URL("https://valid.url"));
    createProjectRepository.save(testProject);
  }

  @Test
  void createFilePatternSuccessfully() throws Exception {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc().perform(post("/projects/0/filePatterns").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void createFilePatternReturnsErrorWhenProjectDoesNotExist() throws Exception {
    CreateFilePatternCommand command =
            new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc().perform(post("/projects/1/filePatterns").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("Project with id 1 not found."));
  }

  @Test
  void createFilePatternReturnsErrorWhenProjectRequestIsInvalid() throws Exception {
    CreateFilePatternCommand command =
            new CreateFilePatternCommand("", InclusionType.INCLUDE);
    mvc().perform(post("/projects/1/filePatterns").content(toJson(command)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

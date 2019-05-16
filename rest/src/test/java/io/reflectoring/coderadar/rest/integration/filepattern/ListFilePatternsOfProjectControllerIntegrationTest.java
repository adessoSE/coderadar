package io.reflectoring.coderadar.rest.integration.filepattern;

import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.CreateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.MalformedURLException;
import java.net.URL;

class ListFilePatternsOfProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired
  private CreateProjectRepository createProjectRepository;

  @Autowired
  private CreateFilePatternRepository createFilePatternRepository;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    Project testProject = new Project();
    testProject.setVcsUrl(new URL("https://valid.url"));
    createProjectRepository.save(testProject);

    FilePattern filePattern = new FilePattern();
    filePattern.setInclusionType(InclusionType.INCLUDE);
    filePattern.setPattern("**/*.java");
    filePattern.setProject(testProject);
    createFilePatternRepository.save(filePattern);

    FilePattern filePattern2 = new FilePattern();
    filePattern2.setInclusionType(InclusionType.EXCLUDE);
    filePattern2.setPattern("**/*.xml");
    filePattern2.setProject(testProject);
    createFilePatternRepository.save(filePattern2);
  }

  @Test
  void listAllFilePatternsOfProjectWithIdZero() throws Exception {
    mvc().perform(get("/projects/0/filePatterns"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(containsResource(GetFilePatternResponse[].class));
  }

  @Test
  void listAllFilePatternsReturnsErrorWhenProjectNotFound() throws Exception {
    mvc().perform(get("/projects/1/filePatterns"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("Project with id 1 not found."));
  }
}

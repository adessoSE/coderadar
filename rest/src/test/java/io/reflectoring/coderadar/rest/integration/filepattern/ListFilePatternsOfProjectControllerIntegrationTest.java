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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ListFilePatternsOfProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateFilePatternRepository createFilePatternRepository;

  @Test
  void listAllFilePatternsOfProjectWithId() throws Exception {
    // Set up
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject = createProjectRepository.save(testProject);

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

    // Test
    mvc()
        .perform(get("/projects/" + testProject.getId() + "/filePatterns"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetFilePatternResponse[].class));
  }

  @Test
  void listAllFilePatternsReturnsErrorWhenProjectNotFound() throws Exception {
    createProjectRepository.deleteAll();

    mvc()
        .perform(get("/projects/1/filePatterns"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }
}

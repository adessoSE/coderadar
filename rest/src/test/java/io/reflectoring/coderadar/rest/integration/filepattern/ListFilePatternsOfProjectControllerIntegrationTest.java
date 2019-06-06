package io.reflectoring.coderadar.rest.integration.filepattern;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.CreateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
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

    FilePattern filePattern = new FilePattern();
    filePattern.setInclusionType(InclusionType.INCLUDE);
    filePattern.setPattern("**/*.java");
    filePattern.setProject(testProject);
    filePattern = createFilePatternRepository.save(filePattern);

    FilePattern filePattern2 = new FilePattern();
    filePattern2.setInclusionType(InclusionType.EXCLUDE);
    filePattern2.setPattern("**/*.xml");
    filePattern2.setProject(testProject);
    filePattern2 = createFilePatternRepository.save(filePattern2);

    testProject.setFilePatterns(Arrays.asList(filePattern, filePattern2));
    testProject = createProjectRepository.save(testProject);

    // Test
    mvc()
        .perform(get("/projects/" + testProject.getId() + "/filePatterns"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetFilePatternResponse[].class))
        .andExpect(
            result -> {
              GetFilePatternResponse[] filePatterns =
                  fromJson(
                      result.getResponse().getContentAsString(), GetFilePatternResponse[].class);
              Assertions.assertEquals(2, filePatterns.length);
            });
  }

  @Test
  void listAllFilePatternsReturnsErrorWhenProjectNotFound() throws Exception {
    mvc()
        .perform(get("/projects/1/filePatterns"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }
}

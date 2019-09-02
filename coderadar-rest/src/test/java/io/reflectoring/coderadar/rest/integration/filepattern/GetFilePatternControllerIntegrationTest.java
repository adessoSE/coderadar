package io.reflectoring.coderadar.rest.integration.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class GetFilePatternControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Autowired private FilePatternRepository filePatternRepository;

  @BeforeEach
  public void setUp() {}

  @Test
  void getFilePatternWithId() throws Exception {
    // Set up
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    FilePatternEntity filePattern = new FilePatternEntity();
    filePattern.setInclusionType(InclusionType.INCLUDE);
    filePattern.setPattern("**/*.java");
    filePattern.setProject(testProject);
    filePattern = filePatternRepository.save(filePattern);

    // Test
    mvc()
        .perform(get("/projects/" + testProject.getId() + "/filePatterns/" + filePattern.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetFilePatternResponse.class))
        .andDo(
            result -> {
              GetFilePatternResponse response =
                  fromJson(result.getResponse().getContentAsString(), GetFilePatternResponse.class);
              Assertions.assertEquals("**/*.java", response.getPattern());
              Assertions.assertEquals(InclusionType.INCLUDE, response.getInclusionType());
            });
  }

  @Test
  void getFilePatternReturnsErrorWhenNotFound() throws Exception {
    mvc()
        .perform(get("/projects/0/filePatterns/2"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("FilePattern with id 2 not found."));
  }
}

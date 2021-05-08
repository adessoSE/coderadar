package io.reflectoring.coderadar.rest.filepattern;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.domain.InclusionType;
import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetFilePatternResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    filePattern = filePatternRepository.save(filePattern);

    // Test
    mvc()
        .perform(
            get("/api/projects/" + testProject.getId() + "/filePatterns/" + filePattern.getId()))
        .andExpect(status().isOk())
        .andExpect(containsResource(GetFilePatternResponse.class))
        .andDo(
            result -> {
              GetFilePatternResponse response =
                  fromJson(result.getResponse().getContentAsString(), GetFilePatternResponse.class);
              Assertions.assertEquals("**/*.java", response.getPattern());
              Assertions.assertEquals(InclusionType.INCLUDE, response.getInclusionType());
            })
        .andDo(document("filepatterns/get"));
  }

  @Test
  void getFilePatternReturnsErrorWhenNotFound() throws Exception {
    mvc()
        .perform(get("/api/projects/0/filePatterns/2"))
        .andExpect(status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("FilePattern with id 2 not found."));
  }
}

package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ListFilePatternsOfProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Autowired private FilePatternRepository filePatternRepository;

  @Test
  void listAllFilePatternsOfProjectWithId() throws Exception {
    // Set up
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");

    FilePatternEntity filePattern = new FilePatternEntity();
    filePattern.setInclusionType(InclusionType.INCLUDE);
    filePattern.setPattern("**/*.java");
    filePattern = filePatternRepository.save(filePattern);

    FilePatternEntity filePattern2 = new FilePatternEntity();
    filePattern2.setInclusionType(InclusionType.EXCLUDE);
    filePattern2.setPattern("**/*.xml");
    filePattern2 = filePatternRepository.save(filePattern2);
    testProject.setFilePatterns(Arrays.asList(filePattern, filePattern2));
    testProject = projectRepository.save(testProject);

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
            })
        .andDo(document("filepatterns/list"));
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

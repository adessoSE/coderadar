package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

class DeleteFilePatternControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Autowired private FilePatternRepository filePatternRepository;

  @Test
  void deleteFilePatternWithId() throws Exception {
    // Set up
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);

    FilePatternEntity filePattern = new FilePatternEntity();
    filePattern.setInclusionType(InclusionType.INCLUDE);
    filePattern.setPattern("**/*.java");
    filePattern.setProject(testProject);
    filePattern = filePatternRepository.save(filePattern);
    final Long id = filePattern.getId();

    mvc()
        .perform(
            delete("/projects/" + testProject.getId() + "/filePatterns/" + filePattern.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            result -> Assertions.assertFalse(filePatternRepository.findById(id).isPresent()));
  }

  @Test
  void deleteFilePatternReturnsErrorWhenNotFound() throws Exception {
    mvc()
        .perform(delete("/projects/0/filePatterns/2"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("FilePattern with id 2 not found."));
  }
}

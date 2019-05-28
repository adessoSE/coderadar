package io.reflectoring.coderadar.rest.integration.filepattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.CreateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class DeleteFilePatternControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateFilePatternRepository createFilePatternRepository;

  @Test
  void deleteFilePatternWithId() throws Exception {
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

    mvc()
        .perform(
            delete("/projects/" + testProject.getId() + "/filePatterns/" + filePattern.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            result -> Assertions.assertFalse(createFilePatternRepository.findById(id).isPresent()));
  }

  @Test
  void deleteFilePatternReturnsErrorWhenNotFound() throws Exception {
    mvc()
        .perform(delete("/projects/0/filePatterns/2"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage")
                .value("FilePattern with id 2 not found."));
  }
}

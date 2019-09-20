package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  // This test has to be annotated with @DirtiesContext because custom Neo4j queries
  // cause problems when run inside an DB tansaction. Therefore the transaction propagation
  // must be set to NOT_SUPPORTED (no transaction) to "fix" this issue.
  @Test
  @DirtiesContext
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public void deleteProjectWithId() throws Exception {
    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject = projectRepository.save(testProject);
    final Long id = testProject.getId();

    mvc()
        .perform(delete("/projects/" + id))
        .andExpect(status().isOk())
        .andDo(
            result -> Assertions.assertFalse(projectRepository.findById(id).isPresent()))
            .andDo(document("projects/delete"));
  }

  @Test
  void deleteProjectReturnsErrorWhenProjectNotFound() throws Exception {
    MvcResult result = mvc().perform(delete("/projects/1"))
        .andExpect(status().isNotFound())
        .andReturn();

    ErrorMessageResponse response = fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class);

    Assertions.assertEquals("Project with id 1 not found.", response.getErrorMessage());
  }
}

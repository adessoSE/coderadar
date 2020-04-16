package io.reflectoring.coderadar.rest.branches;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetBranchResponse;
import java.net.URL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ListBranchesControllerTest extends ControllerTestTemplate {

  @Autowired private CreateProjectService createProjectService;

  private Long projectId;

  @BeforeEach
  void setUp() {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
    projectId =
        createProjectService.createProject(
            new CreateProjectCommand(
                "testProject", null, null, testRepoURL.toString(), false, null, null));
  }

  @Test
  void listAllBranchesOfProjectWithId() throws Exception {
    // Test
    mvc()
        .perform(get("/projects/" + projectId + "/branches"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetBranchResponse[].class))
        .andExpect(
            result -> {
              GetBranchResponse[] branchResponses =
                  fromJson(result.getResponse().getContentAsString(), GetBranchResponse[].class);
              Assertions.assertEquals(3, branchResponses.length);
              Assertions.assertEquals("master", branchResponses[0].getName());
              Assertions.assertEquals("testBranch1", branchResponses[1].getName());
              Assertions.assertEquals("testBranch2", branchResponses[2].getName());
            })
        .andDo(document("branches/list"));
  }

  @Test
  void listAllBranchesOfProjectReturnsErrorWhenProjectNotFound() throws Exception {
    mvc()
        .perform(get("/projects/100/branches"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 100 not found."));
  }
}

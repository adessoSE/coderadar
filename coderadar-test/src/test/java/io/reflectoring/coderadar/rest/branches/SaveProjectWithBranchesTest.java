package io.reflectoring.coderadar.rest.branches;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class SaveProjectWithBranchesTest extends ControllerTestTemplate {
  @Autowired private CommitRepository commitRepository;
  @Autowired private BranchRepository branchRepository;

  @Test
  void createProjectWithBranchesSuccessfully() throws Exception {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", testRepoURL.toString(), false, null, null);
    mvc()
        .perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(
            result -> {
              Long id =
                  fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
              List<BranchEntity> branches = branchRepository.getBranchesInProjectSortedByName(id);
              Assertions.assertEquals(3L, branches.size());
              Assertions.assertEquals("master", branches.get(0).getName());
              Assertions.assertEquals("testBranch1", branches.get(1).getName());
              Assertions.assertEquals("testBranch2", branches.get(2).getName());

              List<CommitEntity> commitsOnMaster =
                  commitRepository.findByProjectIdAndBranchName(id, "master");
              List<CommitEntity> commitsOnTestBranch1 =
                  commitRepository.findByProjectIdAndBranchName(id, "testBranch1");
              List<CommitEntity> commitsOnTestBranch2 =
                  commitRepository.findByProjectIdAndBranchName(id, "testBranch2");

              Assertions.assertEquals(14L, commitsOnMaster.size());
              Assertions.assertEquals(13L, commitsOnTestBranch1.size());
              Assertions.assertEquals(14L, commitsOnTestBranch2.size());
            });
  }
}

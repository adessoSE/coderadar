package io.reflectoring.coderadar.rest.branches;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.DeleteBranchPort;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.ListBranchesPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import java.net.URL;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteBranchTest extends ControllerTestTemplate {

  @Autowired private CreateProjectService createProjectService;

  @Autowired private FileRepository fileRepository;
  @Autowired private CommitRepository commitRepository;
  @Autowired private ListBranchesPort listBranchesPort;
  @Autowired private DeleteBranchPort deleteBranchPort;

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
  void testDeleteBranch() {
    deleteBranchPort.delete(
        projectId, new Branch("testBranch2", "fcd9a0e7c34086fdb0aedc82497f8ddfa142e961"));

    Assertions.assertThat(listBranchesPort.listBranchesInProject(projectId))
        .containsExactlyInAnyOrder(
            new Branch("testBranch1", "d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df"),
            new Branch("master", "e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382"));

    Assertions.assertThat(commitRepository.findByProjectId(projectId)).hasSize(14);
    Assertions.assertThat(fileRepository.findAllinProject(projectId)).hasSize(8);
  }

  @Test
  void testDeleteBranchThatHasChildren() {
    deleteBranchPort.delete(
        projectId, new Branch("testBranch1", "d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df"));

    Assertions.assertThat(listBranchesPort.listBranchesInProject(projectId))
        .containsExactlyInAnyOrder(
            new Branch("testBranch2", "fcd9a0e7c34086fdb0aedc82497f8ddfa142e961"),
            new Branch("master", "e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382"));

    Assertions.assertThat(commitRepository.findByProjectId(projectId)).hasSize(15);
    Assertions.assertThat(fileRepository.findAllinProject(projectId)).hasSize(9);
  }

  @Test
  void testDeleteBranchWithFileOnlyModified() {
    deleteBranchPort.delete(
        projectId, new Branch("master", "e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382"));

    Assertions.assertThat(listBranchesPort.listBranchesInProject(projectId))
        .containsExactlyInAnyOrder(
            new Branch("testBranch2", "fcd9a0e7c34086fdb0aedc82497f8ddfa142e961"),
            new Branch("testBranch1", "d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df"));

    Assertions.assertThat(commitRepository.findByProjectId(projectId)).hasSize(14);
    Assertions.assertThat(fileRepository.findAllinProject(projectId)).hasSize(9);
  }
}

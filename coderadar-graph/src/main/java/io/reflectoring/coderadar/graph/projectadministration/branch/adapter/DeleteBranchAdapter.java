package io.reflectoring.coderadar.graph.projectadministration.branch.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.DeleteBranchPort;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteBranchAdapter implements DeleteBranchPort {

  private final BranchRepository branchRepository;
  private final CommitRepository commitRepository;
  private final MetricRepository metricRepository;

  @Override
  public void delete(long projectId, Branch branch) {
    CommitEntity branchCommit = branchRepository.getBranchCommit(projectId, branch.getName());
    BranchEntity branchEntity =
        branchRepository.findBranchInProjectByName(projectId, branch.getName());
    branchRepository.delete(branchEntity);
    List<BranchEntity> branchesInProject = branchRepository.getBranchesInProject(projectId);
    deleteCommits(branchCommit, branchesInProject);
  }

  /**
   * Recursive method that walks the parents of the head of the branch and deletes them if: The
   * commit has no children. There is no branch pointer on the commit.
   *
   * <p>Also deletes any files that have been added or renamed as those are the only change types
   * for which new nodes are created and would therefore be no longer needed.
   *
   * <p>Metrics attached to the commit are also deleted if they exist.
   *
   * @param commit The commit to delete.
   * @param branchesInProject The currently available branches in the project.
   */
  private void deleteCommits(CommitEntity commit, List<BranchEntity> branchesInProject) {
    List<CommitEntity> children = commitRepository.getCommitChildren(commit.getId());
    if (children != null
        && commitRepository.getCommitChildren(commit.getId()).isEmpty()
        && branchesInProject.stream().noneMatch(b -> b.getCommitHash().equals(commit.getHash()))) {
      List<CommitEntity> parents = commitRepository.getCommitParents(commit.getId());
      metricRepository.deleteMetricsForCommit(commit.getId());
      commitRepository.deleteCommitAndAddedOrRenamedFiles(commit.getId());
      for (CommitEntity parent : parents) {
        deleteCommits(parent, branchesInProject);
      }
    }
  }
}

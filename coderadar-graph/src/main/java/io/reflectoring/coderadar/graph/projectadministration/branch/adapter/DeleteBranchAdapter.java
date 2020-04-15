package io.reflectoring.coderadar.graph.projectadministration.branch.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.DeleteBranchPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeleteBranchAdapter implements DeleteBranchPort {

  private final BranchRepository branchRepository;
  private final CommitRepository commitRepository;
  private final MetricRepository metricRepository;

  public DeleteBranchAdapter(
      BranchRepository branchRepository,
      CommitRepository commitRepository,
      MetricRepository metricRepository) {
    this.branchRepository = branchRepository;
    this.commitRepository = commitRepository;
    this.metricRepository = metricRepository;
  }

  @Override
  public void delete(long projectId, Branch branch) {
    CommitEntity branchCommit = branchRepository.getBranchCommit(projectId, branch.getName());
    BranchEntity branchEntity =
        branchRepository.findBranchInProjectByName(projectId, branch.getName());
    branchRepository.delete(branchEntity);
    List<BranchEntity> branchesInProject = branchRepository.getBranchesInProject(projectId);
    deleteCommits(branchCommit, branchesInProject);
  }

  private void deleteCommits(CommitEntity commit, List<BranchEntity> branchesInProject) {
    List<CommitEntity> children = commitRepository.getCommitChildren(commit.getId());
    if (children != null
        && commitRepository.getCommitChildren(commit.getId()).isEmpty()
        && branchesInProject.stream().noneMatch(b -> b.getCommitHash().equals(commit.getName()))) {
      List<CommitEntity> parents = commitRepository.getCommitParents(commit.getId());
      metricRepository.deleteMetricsForCommit(commit.getId());
      commitRepository.deleteCommitAndAddedOrRenamedFiles(commit.getId());
      for (CommitEntity parent : parents) {
        deleteCommits(parent, branchesInProject);
      }
    }
  }
}

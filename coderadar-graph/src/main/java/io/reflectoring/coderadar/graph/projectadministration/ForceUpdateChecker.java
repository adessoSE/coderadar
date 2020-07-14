package io.reflectoring.coderadar.graph.projectadministration;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ForceUpdateChecker {
  private final CommitRepository commitRepository;

  public ForceUpdateChecker(CommitRepository commitRepository) {
    this.commitRepository = commitRepository;
  }

  public List<CommitEntity> getUnreachableCommits(long projectId, Set<String> newCommitHashes) {
    List<CommitEntity> unreachableCommits = new ArrayList<>();
    List<CommitEntity> commits = commitRepository.findByProjectId(projectId);
    for (CommitEntity commit : commits) {
      if (!newCommitHashes.contains(commit.getHash())) {
        unreachableCommits.add(commit);
      }
    }
    return unreachableCommits;
  }
}

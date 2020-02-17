package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.GetProjectHeadCommitPort;
import org.springframework.stereotype.Service;

@Service
public class GetProjectHeadCommitAdapter implements GetProjectHeadCommitPort {

  private final CommitRepository commitRepository;
  private final CommitBaseDataMapper commitBaseDataMapper = new CommitBaseDataMapper();

  public GetProjectHeadCommitAdapter(CommitRepository commitRepository) {
    this.commitRepository = commitRepository;
  }

  @Override
  public Commit getHeadCommit(Long projectId, String branch) {
    return commitBaseDataMapper.mapNodeEntity(commitRepository.findHeadCommit(projectId, branch));
  }
}

package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.GetProjectHeadCommitPort;
import org.springframework.stereotype.Service;

@Service
public class GetProjectHeadCommitAdapter implements GetProjectHeadCommitPort {

  private final CommitRepository commitRepository;

  public GetProjectHeadCommitAdapter(CommitRepository commitRepository) {
    this.commitRepository = commitRepository;
  }

  @Override
  public Commit getHeadCommit(Long projectId) {
    return CommitBaseDataMapper.mapCommitEntity(commitRepository.findHeadCommit(projectId));
  }
}

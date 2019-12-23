package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.query.port.driver.GetCommitsInProjectUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetCommitsInProjectService implements GetCommitsInProjectUseCase {
  private final GetCommitsInProjectPort getCommitsInProjectPort;

  public GetCommitsInProjectService(GetCommitsInProjectPort getCommitsInProjectPort) {
    this.getCommitsInProjectPort = getCommitsInProjectPort;
  }

  @Override
  public List<Commit> get(Long projectId) {
    return getCommitsInProjectPort.getCommitsSortedByTimestampDescWithNoRelationships(projectId);
  }
}

package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.query.port.driver.GetCommitResponse;
import io.reflectoring.coderadar.query.port.driver.GetCommitsInProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCommitsInProjectService implements GetCommitsInProjectUseCase {
  private final GetCommitsInProjectPort getCommitsInProjectPort;

  @Autowired
  public GetCommitsInProjectService(GetCommitsInProjectPort getCommitsInProjectPort) {
    this.getCommitsInProjectPort = getCommitsInProjectPort;
  }

  @Override
  public List<GetCommitResponse> get(Long projectId) {
    return getCommitsInProjectPort.getCommitsResponseSortedByTimestampDesc(projectId);
  }
}

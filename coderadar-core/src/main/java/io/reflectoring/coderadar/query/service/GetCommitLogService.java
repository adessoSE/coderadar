package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.query.port.driven.GetCommitLogPort;
import io.reflectoring.coderadar.query.port.driver.GetCommitLogUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCommitLogService implements GetCommitLogUseCase {

  private final GetCommitLogPort getCommitLogPort;
  private final GetProjectPort getProjectPort;

  public GetCommitLogService(GetCommitLogPort getCommitLogPort, GetProjectPort getProjectPort) {
    this.getCommitLogPort = getCommitLogPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<CommitLog> getCommitLog(long projectId) {
    if (getProjectPort.existsById(projectId)) {
      return getCommitLogPort.getCommitLog(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

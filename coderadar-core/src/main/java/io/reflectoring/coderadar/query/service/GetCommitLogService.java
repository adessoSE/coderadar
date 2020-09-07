package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.query.port.driven.GetCommitLogPort;
import io.reflectoring.coderadar.query.port.driver.GetCommitLogUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCommitLogService implements GetCommitLogUseCase {

  private final GetCommitLogPort getCommitLogPort;
  private final GetProjectPort getProjectPort;

  @Override
  public List<CommitLog> getCommitLog(long projectId) {
    if (getProjectPort.existsById(projectId)) {
      return getCommitLogPort.getCommitLog(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

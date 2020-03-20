package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.query.port.driven.GetCommitLogPort;
import io.reflectoring.coderadar.query.port.driver.GetCommitLogUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetCommitLogService implements GetCommitLogUseCase {

  private final GetCommitLogPort getCommitLogPort;
  private final GetProjectPort getProjectPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  public GetCommitLogService(
      GetCommitLogPort getCommitLogPort,
      GetProjectPort getProjectPort,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.getCommitLogPort = getCommitLogPort;
    this.getProjectPort = getProjectPort;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public List<CommitLog> getCommitLog(long projectId) {
    Project project = getProjectPort.get(projectId);
    return getCommitLogPort.getCommitLog(
        coderadarConfigurationProperties.getWorkdir().toString()
            + "/projects/"
            + project.getWorkdirName());
  }
}

package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.query.port.driven.GetCommitLogPort;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.query.port.driver.GetCommitLogUseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GetCommitLogService implements GetCommitLogUseCase {

  private final GetCommitLogPort getCommitLogPort;
  private final GetProjectPort getProjectPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final GetCommitsInProjectPort getCommitsInProjectPort;

  public GetCommitLogService(
      GetCommitLogPort getCommitLogPort,
      GetProjectPort getProjectPort,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      GetCommitsInProjectPort getCommitsInProjectPort) {
    this.getCommitLogPort = getCommitLogPort;
    this.getProjectPort = getProjectPort;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.getCommitsInProjectPort = getCommitsInProjectPort;
  }

  @Override
  public List<CommitLog> getCommitLog(long projectId) {
    Project project = getProjectPort.get(projectId);
    List<Commit> commits = getCommitsInProjectPort.getAllCommitsWithNoRelationships(projectId);
    List<CommitLog> commitLogs =
        getCommitLogPort.getCommitLog(
            coderadarConfigurationProperties.getWorkdir().toString()
                + "/projects/"
                + project.getWorkdirName());

    for (CommitLog commitLog : commitLogs) {
      Optional<Commit> entity =
          commits.stream()
              .filter(commit -> commit.getName().equals(commitLog.getHash()))
              .findFirst();
      if (entity.isPresent()) {
        commitLog.setAnalyzed(entity.get().isAnalyzed());
      } else {
        commitLog.setAnalyzed(false);
      }
    }
    return commitLogs;
  }
}

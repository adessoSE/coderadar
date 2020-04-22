package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.FileContentWithMetrics;
import io.reflectoring.coderadar.query.port.driver.filediff.GetFileDiffCommand;
import io.reflectoring.coderadar.query.port.driver.filediff.GetFileDiffUseCase;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import org.springframework.stereotype.Service;

@Service
public class GetFileDiffService implements GetFileDiffUseCase {

  private final GetProjectPort getProjectPort;
  private final GetRawCommitContentPort getRawCommitContentPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  public GetFileDiffService(
      GetProjectPort getProjectPort,
      GetRawCommitContentPort getRawCommitContentPort,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.getProjectPort = getProjectPort;
    this.getRawCommitContentPort = getRawCommitContentPort;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public FileContentWithMetrics getFileDiff(long projectId, GetFileDiffCommand command) {
    Project project = getProjectPort.get(projectId);
    String workdir =
        coderadarConfigurationProperties.getWorkdir() + "/projects/" + project.getWorkdirName();
    String content =
        new String(
            getRawCommitContentPort.getFileDiff(
                workdir, command.getFilepath(), command.getCommitHash()));
    return new FileContentWithMetrics(content, null);
  }
}

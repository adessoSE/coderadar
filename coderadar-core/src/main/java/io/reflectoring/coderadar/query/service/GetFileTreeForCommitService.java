package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.analyzer.MisconfigurationException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.FileTree;
import io.reflectoring.coderadar.query.port.driven.GetFileTreeForCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetFileTreeForCommitUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetFileTreeForCommitService implements GetFileTreeForCommitUseCase {
  private final GetProjectPort getProjectPort;
  private final GetFileTreeForCommitPort getFileTreeForCommitPort;
  private final ListFilePatternsOfProjectPort listFilePatternsOfProjectPort;

  public GetFileTreeForCommitService(
      GetProjectPort getProjectPort,
      GetFileTreeForCommitPort getFileTreeForCommitPort,
      ListFilePatternsOfProjectPort listFilePatternsOfProjectPort) {
    this.getProjectPort = getProjectPort;
    this.getFileTreeForCommitPort = getFileTreeForCommitPort;
    this.listFilePatternsOfProjectPort = listFilePatternsOfProjectPort;
  }

  @Override
  public FileTree getFileTreeForCommit(long projectId, String commitHash) {
    if (getProjectPort.existsById(projectId)) {
      List<FilePattern> patterns = listFilePatternsOfProjectPort.listFilePatterns(projectId);
      if (patterns.isEmpty()) {
        throw new MisconfigurationException("No file patterns configured for this project.");
      }
      return getFileTreeForCommitPort.getFileTreeForCommit(projectId, commitHash, patterns);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

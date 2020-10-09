package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.ValidationUtils;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.FileTree;
import io.reflectoring.coderadar.query.port.driven.GetFileTreeForCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetFileTreeForCommitUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFileTreeForCommitService implements GetFileTreeForCommitUseCase {
  private final GetProjectPort getProjectPort;
  private final GetFileTreeForCommitPort getFileTreeForCommitPort;

  @Override
  public FileTree getFileTreeForCommit(
      long projectId, String commitHash, boolean changedFilesOnly) {
    commitHash = ValidationUtils.validateAndTrimCommitHash(commitHash);
    if (getProjectPort.existsById(projectId)) {
      return getFileTreeForCommitPort.getFileTreeForCommit(projectId, commitHash, changedFilesOnly);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

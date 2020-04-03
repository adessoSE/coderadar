package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.FileTree;

public interface GetFileTreeForCommitUseCase {
  FileTree getFileTreeForCommit(long projectId, String commitHash);
}

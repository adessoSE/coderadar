package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.FileTree;

public interface GetFileTreeForCommitPort {
  FileTree getFileTreeForCommit(long projectId, String commitHash);
}

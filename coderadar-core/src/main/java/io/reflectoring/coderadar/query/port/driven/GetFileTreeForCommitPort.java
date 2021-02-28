package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.FileTree;

public interface GetFileTreeForCommitPort {

  /**
   * @param projectId The id of the project.
   * @param commitHash The commit whose file tree to list.
   * @param changedFilesOnly Return only the files changed in the commit.
   * @return A recursive tree structure describing the files and directories in the commit.
   */
  FileTree getFileTreeForCommit(long projectId, long commitHash, boolean changedFilesOnly);
}

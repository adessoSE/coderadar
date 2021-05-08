package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.domain.FileTree;

public interface GetFileTreeForCommitUseCase {

  /**
   * @param projectId The id of the project.
   * @param commitHash The commit whose file tree to list.
   * @param changedFilesOnly Return only files changed in this commit.
   * @return A recursive tree structure describing the files and directories in the commit.
   */
  FileTree getFileTreeForCommit(long projectId, String commitHash, boolean changedFilesOnly);
}

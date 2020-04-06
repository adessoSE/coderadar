package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.query.domain.FileTree;
import java.util.List;

public interface GetFileTreeForCommitPort {

  /**
   * @param projectId The id of the project.
   * @param commitHash The commit whose file tree to list.
   * @param filePatterns Only files that match these patterns will be returned.
   * @return A recursive tree structure describing the files and directories in the commit.
   */
  FileTree getFileTreeForCommit(long projectId, String commitHash, List<FilePattern> filePatterns);
}

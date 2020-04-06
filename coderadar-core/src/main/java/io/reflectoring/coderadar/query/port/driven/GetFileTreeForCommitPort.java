package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.query.domain.FileTree;
import java.util.List;

public interface GetFileTreeForCommitPort {
  FileTree getFileTreeForCommit(long projectId, String commitHash, List<FilePattern> filePatterns);
}

package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import java.util.List;

public interface GetCriticalFilesPort {
  List<ContributorsForFile> getCriticalFiles(
      Long projectId, int numberOfContributors, String commitHash, List<FilePattern> filePatterns);
}

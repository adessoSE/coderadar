package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.domain.FileAndCommitsForTimePeriod;
import java.util.Date;
import java.util.List;

public interface GetCriticalFilesPort {
  List<ContributorsForFile> getFilesWithManyContributors(
      long projectId, int numberOfContributors, String commitHash, List<FilePattern> filePatterns);

  List<FileAndCommitsForTimePeriod> getFrequentlyChangedFiles(
      long projectId,
      String commitHash,
      Date startDate,
      int frequency,
      List<FilePattern> filePatterns);
}

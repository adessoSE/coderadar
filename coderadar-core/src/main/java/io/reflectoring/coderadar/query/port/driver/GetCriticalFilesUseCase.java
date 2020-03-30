package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.domain.FileAndCommitsForTimePeriod;
import java.util.List;

public interface GetCriticalFilesUseCase {
  List<ContributorsForFile> getFilesWithManyContributors(
      long projectId, GetFilesWithManyContributorsCommand command);

  List<FileAndCommitsForTimePeriod> getFrequentlyChangedFiles(
      long projectId, GetFrequentlyChangedFilesCommand command);
}

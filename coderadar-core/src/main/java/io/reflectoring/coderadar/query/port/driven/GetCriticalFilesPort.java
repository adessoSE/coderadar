package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.domain.ContributorsForFile;
import io.reflectoring.coderadar.domain.FileAndCommitsForTimePeriod;
import io.reflectoring.coderadar.domain.FilePattern;
import java.util.Date;
import java.util.List;

public interface GetCriticalFilesPort {
  /**
   * Search for files that are changed by n contributors, where n = numberOfContributors.
   *
   * @param projectId The id of the project.
   * @param numberOfContributors The amount of contributors for which a file is considered critical.
   * @param commitHash Only search for critical files in the file tree of the given commit.
   * @param filePatterns Files have to match those file patterns.
   * @return the filepath and the contributors who changed those files.
   */
  List<ContributorsForFile> getFilesWithContributors(
      long projectId, int numberOfContributors, long commitHash, List<FilePattern> filePatterns);

  /**
   * Search from the start date to the date of the given commit for files that are changed >=
   * frequency times.
   *
   * @param projectId The id of the project.
   * @param commitHash Only search for critical files in the file tree of the given commit.
   * @param startDate Start date to search from.
   * @param frequency The amount of changes to a file for which it is considered critical.
   * @param filePatterns Files have to match those file patterns.
   * @return the filepath and the commits in which those files are changed.
   */
  List<FileAndCommitsForTimePeriod> getFrequentlyChangedFiles(
      long projectId,
      long commitHash,
      Date startDate,
      int frequency,
      List<FilePattern> filePatterns);
}

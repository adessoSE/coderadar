package io.reflectoring.coderadar.query.port.driver.fileswithcontributors;

import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.domain.FileAndCommitsForTimePeriod;
import java.util.List;

public interface GetCriticalFilesUseCase {
  /**
   * Search for files that are changed by n contributors, where n = numberOfContributors.
   *
   * @param projectId The id of the project.
   * @param command containing: commitHash: Only search for critical files in the file tree of the
   *     given commit. numberOfContributors: The amount of contributors for which a file is
   *     considered critical.
   * @return the filepath and the contributors who changed those files.
   */
  List<ContributorsForFile> getFilesWithContributors(
      long projectId, GetFilesWithContributorsCommand command);

  /**
   * Search from the start date to the date of the given commit for files that are changed >=
   * frequency times.
   *
   * @param projectId The id of the project.
   * @param command containing: commitHash: Only search for critical files in the file tree of the
   *     given commit. startDate: Start date to search from. frequency: The amount of changes to a
   *     file for which it is considered critical.
   * @return the filepath and the commits in which those files are changed.
   */
  List<FileAndCommitsForTimePeriod> getFrequentlyChangedFiles(
      long projectId, GetFrequentlyChangedFilesCommand command);
}

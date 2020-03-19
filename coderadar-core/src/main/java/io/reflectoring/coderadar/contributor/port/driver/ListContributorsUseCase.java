package io.reflectoring.coderadar.contributor.port.driver;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import java.util.List;

public interface ListContributorsUseCase {
  /**
   * @param projectId The project id.
   * @return list of contributors who work on the corresponding project
   */
  List<Contributor> listContributors(long projectId);

  /**
   * @param projectId The project id.
   * @param command Contains the filename we are looking for.
   * @return list of contributors that have made changes to the file with the given filename
   */
  List<Contributor> listContributorsForProjectAndFilepathInCommit(
      long projectId, GetContributorsForFileCommand command);
}

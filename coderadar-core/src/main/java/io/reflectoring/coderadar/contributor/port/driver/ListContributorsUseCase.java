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
   * @param command Contains the filepath/directory path we are looking for.
   * @return list of contributors that have made changes to the given file or directory.
   */
  List<Contributor> listContributorsForProjectAndPathInCommit(
      long projectId, GetContributorsForPathCommand command);
}

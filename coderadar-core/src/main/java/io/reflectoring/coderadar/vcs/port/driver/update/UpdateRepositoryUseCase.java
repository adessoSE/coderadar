package io.reflectoring.coderadar.vcs.port.driver.update;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import java.util.List;

public interface UpdateRepositoryUseCase {

  /**
   * Updates a local git repository (git pull against remote)
   *
   * @param command The command with the required parameters
   * @throws UnableToUpdateRepositoryException Thrown if there is an error while updating the
   *     repository.
   * @return Returns a list of branches that were updated.
   */
  List<Branch> updateRepository(UpdateRepositoryCommand command)
      throws UnableToUpdateRepositoryException;
}

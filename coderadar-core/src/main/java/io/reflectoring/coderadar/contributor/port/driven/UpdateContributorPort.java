package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorCommand;

public interface UpdateContributorPort {
  /**
   * Updates the contributor with the given id.
   *
   * @param id The id of the contributor.
   * @param command The data to update.
   */
  void updateContributor(long id, UpdateContributorCommand command);
}

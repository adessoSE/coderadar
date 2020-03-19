package io.reflectoring.coderadar.contributor.port.driver;

public interface UpdateContributorUseCase {

  /**
   * Updates the contributor with the given id.
   *
   * @param id The id of the contributor.
   * @param command The data to update.
   */
  void updateContributor(long id, UpdateContributorCommand command);
}

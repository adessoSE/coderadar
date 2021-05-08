package io.reflectoring.coderadar.contributor.port.driver;

import io.reflectoring.coderadar.domain.Contributor;

public interface GetContributorUseCase {
  /**
   * @param id of the contributor.
   * @return Contributor with the supplied id.
   */
  Contributor getById(long id);
}

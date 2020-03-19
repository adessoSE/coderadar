package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;

public interface GetContributorPort {
  /**
   * @param id of the contributor.
   * @return Contributor with the supplied id.
   */
  Contributor get(long id);
}

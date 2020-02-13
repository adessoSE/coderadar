package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;

public interface GetContributorPort {
  Contributor getContributorById(Long id);
}

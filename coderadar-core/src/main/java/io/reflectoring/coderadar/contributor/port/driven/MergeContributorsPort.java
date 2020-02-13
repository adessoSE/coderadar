package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;

public interface MergeContributorsPort {
  void mergeContributors(Contributor first, Contributor second);
}

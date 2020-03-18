package io.reflectoring.coderadar.contributor.port.driven;

import java.util.List;

public interface MergeContributorsPort {
  /**
   * Merges two contributors to one. The resulting contributor keeps the first of the given IDs and
   * has the given display name. The second contributor id becomes invalid.
   *
   * @param contributorIds The ids of the contributors to merge.
   * @param displayName The new display name of the merged contributor.
   */
  void mergeContributors(List<Long> contributorIds, String displayName);
}

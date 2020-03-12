package io.reflectoring.coderadar.contributor.port.driven;

public interface MergeContributorsPort {
  /**
   * Merges two contributors to one. The resulting contributor keeps the first of the given IDs and
   * has the given display name. The second contributor id becomes invalid.
   *
   * @param firstId The id of the first contributor.
   * @param secondId The id of the second contributor.
   * @param displayName The new display name of the merged contributor.
   */
  void mergeContributors(long firstId, long secondId, String displayName);
}

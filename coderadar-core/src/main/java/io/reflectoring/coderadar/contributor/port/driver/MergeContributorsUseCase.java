package io.reflectoring.coderadar.contributor.port.driver;

public interface MergeContributorsUseCase {
  /**
   * Merges two contributors to one. The resulting contributor keeps the first of the given IDs and
   * has the given display name. The second contributor id becomes invalid.
   *
   * @param command contains the IDs of the two contributors that should be merged, and the new
   *     displayName of the merged contributor.
   */
  void mergeContributors(MergeContributorsCommand command);
}

package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import java.util.List;

public interface GetCommitsInProjectPort {

  /**
   * Returns all commits in a project/branch without initializing any relationships.
   *
   * @param projectId The project id.
   * @param branch The branch name.
   * @return A list of commit domain objects with no relationships.
   */
  List<Commit> getCommitsSortedByTimestampDescWithNoRelationships(long projectId, String branch);

  /**
   * Returns all not yet analyzed commits in this project, that match the supplied file patterns and
   * are in the specified branch.
   *
   * @param projectId The id of the project.
   * @param filePatterns The patterns to use.
   * @return A list of commits with initialized FileToCommitRelationShips and no parents.
   */
  List<Commit> getNonAnalyzedSortedByTimestampAscWithNoParents(
      long projectId, List<FilePattern> filePatterns, String branch);
}

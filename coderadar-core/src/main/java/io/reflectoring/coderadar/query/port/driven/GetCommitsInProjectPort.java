package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.analyzer.domain.AnalyzeCommitDto;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.query.domain.CommitResponse;
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
   * Returns all commits in a project/branch.
   *
   * @param projectId The project id.
   * @param branch The branch name.
   * @return A list of commit response objects with no relationships sorted by timestamp descending.
   */
  CommitResponse[] getCommitResponses(long projectId, String branch);

  /**
   * Returns all not yet analyzed commits in this project, that match the supplied file patterns and
   * are in the specified branch.
   *
   * @param projectId The id of the project.
   * @param filePatterns The patterns to use.
   * @return A list of commits with initialized FileToCommitRelationShips and no parents.
   */
  AnalyzeCommitDto[] getNonAnalyzedSortedByTimestampAscWithNoParents(
      long projectId, List<FilePattern> filePatterns, String branch);

  /**
   * Returns all commits in a project/branch for a contributor.
   *
   * @param projectId The project id.
   * @param branchName The branch name.
   * @param email The email of the contributor.
   * @return A list of commit response objects sorted by timestamp descending.
   */
  CommitResponse[] getCommitsForContributorResponses(
      long projectId, String branchName, String email);
}

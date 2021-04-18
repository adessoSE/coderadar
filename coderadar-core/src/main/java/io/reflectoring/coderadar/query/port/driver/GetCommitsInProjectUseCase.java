package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.domain.CommitResponse;

public interface GetCommitsInProjectUseCase {

  /**
   * Returns all commits in a project/branch.
   *
   * @param projectId The project id.
   * @param branch The branch name.
   * @return A list of commit response objects with no relationships.
   */
  CommitResponse[] get(long projectId, String branch);

  /**
   * Returns all commits in a project/branch for the contributor with the given email.
   *
   * @param projectId The project id.
   * @param branchName The branch name.
   * @param email The email of the contributor.
   * @return A list of commit response objects with no relationships.
   */
  CommitResponse[] getForContributor(long projectId, String branchName, String email);
}

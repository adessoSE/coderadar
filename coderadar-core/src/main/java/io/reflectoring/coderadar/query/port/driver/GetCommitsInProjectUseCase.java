package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.domain.CommitResponse;

import java.util.List;

public interface GetCommitsInProjectUseCase {

  /**
   * Returns all commits in a project/branch without initializing any relationships.
   *
   * @param projectId The project id.
   * @param branch The branch name.
   * @return A list of commit domain objects with no relationships.
   */
  List<Commit> get(long projectId, String branch);

  CommitResponse[] getResponses(long projectId, String branch);

    /**
     * Returns all commits in a project/branch for the contributor with the given email without
     * initializing any relationships.
     *
     * @param projectId The project id.
     * @param branchName The branch name.
     * @param email The email of the contributor.
     * @return A list of commit domain objects with no relationships.
     */
  List<Commit> getForContributor(long projectId, String branchName, String email);
}

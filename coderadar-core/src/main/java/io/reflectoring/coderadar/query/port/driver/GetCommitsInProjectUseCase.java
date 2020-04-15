package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
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
}

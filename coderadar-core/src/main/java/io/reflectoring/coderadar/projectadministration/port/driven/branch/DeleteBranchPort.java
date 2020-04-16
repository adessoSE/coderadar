package io.reflectoring.coderadar.projectadministration.port.driven.branch;

import io.reflectoring.coderadar.projectadministration.domain.Branch;

public interface DeleteBranchPort {

  /**
   * Deletes a branch in a project. Any commits that only exist as part of the branch are also
   * deleted.
   *
   * @param projectId The id of the project.
   * @param branch The branch to delete.
   */
  void delete(long projectId, Branch branch);
}

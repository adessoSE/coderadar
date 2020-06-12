package io.reflectoring.coderadar.projectadministration.port.driven.branch;

import io.reflectoring.coderadar.projectadministration.domain.Branch;

import java.util.List;

public interface ListBranchesPort {

  /**
   * NOTE: Do not confuse this with
   *
   * @see io.reflectoring.coderadar.vcs.port.driven.GetAvailableBranchesPort This method only
   *     returns branches saved in the database and has no knowedge of the local repository.
   * @param projectId The project id.
   * @return All of the branches in the project.
   */
  List<Branch> listBranchesInProject(long projectId);
}

package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Branch;

import java.util.List;

public interface GetAvailableBranchesPort {

  /**
   * NOTE: branch names such as "refs/origin/master" will be shortened to "master"
   *
   * @param repositoryRoot The local git repository to check.
   * @return All of the branches available in the local repository.
   */
  List<Branch> getAvailableBranches(String repositoryRoot);
}

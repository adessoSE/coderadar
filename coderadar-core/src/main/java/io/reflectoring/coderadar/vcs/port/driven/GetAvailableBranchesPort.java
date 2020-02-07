package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import java.io.File;
import java.util.List;

public interface GetAvailableBranchesPort {
  List<Branch> getAvailableBranches(File repositoryRoot);
}

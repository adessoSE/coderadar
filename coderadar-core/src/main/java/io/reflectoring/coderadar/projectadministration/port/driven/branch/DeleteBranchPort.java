package io.reflectoring.coderadar.projectadministration.port.driven.branch;

import io.reflectoring.coderadar.projectadministration.domain.Branch;

public interface DeleteBranchPort {
  void delete(long projectId, Branch branch);
}

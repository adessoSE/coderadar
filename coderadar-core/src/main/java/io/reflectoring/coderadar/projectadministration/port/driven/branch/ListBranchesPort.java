package io.reflectoring.coderadar.projectadministration.port.driven.branch;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import java.util.List;

public interface ListBranchesPort {
  List<Branch> listBranchesInProject(long projectId);
}

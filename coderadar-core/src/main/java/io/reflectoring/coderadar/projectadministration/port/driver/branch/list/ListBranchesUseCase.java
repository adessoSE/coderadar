package io.reflectoring.coderadar.projectadministration.port.driver.branch.list;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import java.util.List;

public interface ListBranchesUseCase {
  List<Branch> listBranchesInProject(long projectId);
}

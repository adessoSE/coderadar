package io.reflectoring.coderadar.projectadministration.port.driver.branch.list;

import io.reflectoring.coderadar.domain.Branch;
import java.util.List;

public interface ListBranchesUseCase {
  List<Branch> listBranchesInProject(long projectId);
}

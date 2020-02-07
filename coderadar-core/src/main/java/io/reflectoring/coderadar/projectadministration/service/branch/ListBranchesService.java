package io.reflectoring.coderadar.projectadministration.service.branch;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.ListBranchesPort;
import io.reflectoring.coderadar.projectadministration.port.driver.branch.get.ListBranchesUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListBranchesService implements ListBranchesUseCase {

  private final ListBranchesPort listBranchesPort;

  public ListBranchesService(ListBranchesPort listBranchesPort) {
    this.listBranchesPort = listBranchesPort;
  }

  @Override
  public List<Branch> listBranchesInProject(long projectId) {
    return listBranchesPort.listBranchesInProject(projectId);
  }
}

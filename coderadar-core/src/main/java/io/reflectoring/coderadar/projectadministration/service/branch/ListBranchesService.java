package io.reflectoring.coderadar.projectadministration.service.branch;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.ListBranchesPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.branch.get.ListBranchesUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListBranchesService implements ListBranchesUseCase {

  private final ListBranchesPort listBranchesPort;
  private final GetProjectPort getProjectPort;

  public ListBranchesService(ListBranchesPort listBranchesPort, GetProjectPort getProjectPort) {
    this.listBranchesPort = listBranchesPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<Branch> listBranchesInProject(long projectId) {
    if (getProjectPort.existsById(projectId)) {
      return listBranchesPort.listBranchesInProject(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

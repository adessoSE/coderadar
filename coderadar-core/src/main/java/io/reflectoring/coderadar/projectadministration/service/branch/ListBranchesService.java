package io.reflectoring.coderadar.projectadministration.service.branch;

import io.reflectoring.coderadar.domain.Branch;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.ListBranchesPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.branch.list.ListBranchesUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListBranchesService implements ListBranchesUseCase {

  private final ListBranchesPort listBranchesPort;
  private final GetProjectPort getProjectPort;

  @Override
  public List<Branch> listBranchesInProject(long projectId) {
    if (getProjectPort.existsById(projectId)) {
      return listBranchesPort.listBranchesInProject(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

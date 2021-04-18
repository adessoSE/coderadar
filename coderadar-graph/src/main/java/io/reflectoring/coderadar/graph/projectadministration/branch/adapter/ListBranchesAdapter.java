package io.reflectoring.coderadar.graph.projectadministration.branch.adapter;

import io.reflectoring.coderadar.domain.Branch;
import io.reflectoring.coderadar.graph.projectadministration.branch.BranchMapper;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.ListBranchesPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListBranchesAdapter implements ListBranchesPort {

  private final BranchRepository branchRepository;

  private final BranchMapper branchMapper = new BranchMapper();

  @Override
  public List<Branch> listBranchesInProject(long projectId) {
    return branchMapper.mapNodeEntities(
        branchRepository.getBranchesInProjectSortedByName(projectId));
  }
}

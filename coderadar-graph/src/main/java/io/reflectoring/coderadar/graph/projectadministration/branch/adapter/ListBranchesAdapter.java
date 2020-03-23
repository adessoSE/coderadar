package io.reflectoring.coderadar.graph.projectadministration.branch.adapter;

import io.reflectoring.coderadar.graph.projectadministration.branch.BranchMapper;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.ListBranchesPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListBranchesAdapter implements ListBranchesPort {

  private final BranchMapper branchMapper = new BranchMapper();

  private final BranchRepository branchRepository;

  public ListBranchesAdapter(BranchRepository branchRepository) {
    this.branchRepository = branchRepository;
  }

  @Override
  public List<Branch> listBranchesInProject(long projectId) {
    return branchMapper.mapNodeEntities(branchRepository.getBranchesInProject(projectId));
  }
}

package io.reflectoring.coderadar.graph.projectadministration.branch;

import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.projectadministration.domain.Branch;

public class BranchMapper implements Mapper<Branch, BranchEntity> {
  @Override
  public Branch mapNodeEntity(BranchEntity nodeEntity) {
    Branch branch = new Branch();
    branch.setId(nodeEntity.getId());
    branch.setName(nodeEntity.getName());
    return branch;
  }

  @Override
  public BranchEntity mapDomainObject(Branch domainObject) {
    BranchEntity branchEntity = new BranchEntity();
    branchEntity.setName(domainObject.getName());
    return branchEntity;
  }
}

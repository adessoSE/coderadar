package io.reflectoring.coderadar.graph.projectadministration.branch;

import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.projectadministration.domain.Branch;

public class BranchMapper implements Mapper<Branch, BranchEntity> {
  @Override
  public Branch mapGraphObject(BranchEntity nodeEntity) {
    Branch branch = new Branch();
    branch.setName(nodeEntity.getName());
    branch.setCommitHash(nodeEntity.getCommitHash());
    branch.setTag(nodeEntity.isTag());
    return branch;
  }

  @Override
  public BranchEntity mapDomainObject(Branch domainObject) {
    BranchEntity branchEntity = new BranchEntity();
    branchEntity.setName(domainObject.getName());
    branchEntity.setCommitHash(domainObject.getCommitHash());
    branchEntity.setTag(domainObject.isTag());
    return branchEntity;
  }
}

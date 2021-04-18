package io.reflectoring.coderadar.graph.projectadministration.branch;

import io.reflectoring.coderadar.domain.Branch;
import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;

public class BranchMapper implements Mapper<Branch, BranchEntity> {
  @Override
  public Branch mapGraphObject(BranchEntity nodeEntity) {
    return new Branch(nodeEntity.getName(), nodeEntity.getCommitHash(), nodeEntity.isTag());
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

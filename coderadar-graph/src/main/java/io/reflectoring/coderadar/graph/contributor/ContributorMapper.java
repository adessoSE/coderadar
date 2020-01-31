package io.reflectoring.coderadar.graph.contributor;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.graph.AbstractMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;

public class ContributorMapper extends AbstractMapper<Contributor, ContributorEntity> {
  @Override
  public Contributor mapNodeEntity(ContributorEntity nodeEntity) {
    Contributor contributor = new Contributor();
    contributor.setId(nodeEntity.getId());
    contributor.setEmail(nodeEntity.getEmail());
    contributor.setNames(nodeEntity.getNames());
    // set files

    return contributor;
  }

  @Override
  public ContributorEntity mapDomainObject(Contributor domainObject) {
    ContributorEntity contributorEntity = new ContributorEntity();
    contributorEntity.setId(domainObject.getId());
    contributorEntity.setNames(domainObject.getNames());
    contributorEntity.setEmail(domainObject.getEmail());
    // set files
    // set projects
    return contributorEntity;
  }
}

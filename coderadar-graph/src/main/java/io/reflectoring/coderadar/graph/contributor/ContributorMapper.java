package io.reflectoring.coderadar.graph.contributor;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;

public class ContributorMapper implements Mapper<Contributor, ContributorEntity> {
  @Override
  public Contributor mapNodeEntity(ContributorEntity nodeEntity) {
    Contributor contributor = new Contributor();
    contributor.setId(nodeEntity.getId());
    contributor.setEmails(nodeEntity.getEmails());
    contributor.setName(nodeEntity.getName());
    // set files
    // set projects
    return contributor;
  }

  @Override
  public ContributorEntity mapDomainObject(Contributor domainObject) {
    ContributorEntity contributorEntity = new ContributorEntity();
    contributorEntity.setId(domainObject.getId());
    contributorEntity.setName(domainObject.getName());
    contributorEntity.setEmails(domainObject.getEmails());
    // set files
    // set projects
    return contributorEntity;
  }
}

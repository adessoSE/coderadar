package io.reflectoring.coderadar.graph.contributor;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;

public class ContributorMapper implements Mapper<Contributor, ContributorEntity> {
  @Override
  public Contributor mapNodeEntity(ContributorEntity nodeEntity) {
    Contributor contributor = new Contributor();
    contributor.setId(nodeEntity.getId());
    contributor.setDisplayName(nodeEntity.getDisplayName());
    contributor.setEmailAddresses(nodeEntity.getEmails());
    contributor.setNames(nodeEntity.getNames());
    return contributor;
  }

  @Override
  public ContributorEntity mapDomainObject(Contributor domainObject) {
    ContributorEntity contributorEntity = new ContributorEntity();
    contributorEntity.setId(domainObject.getId());
    contributorEntity.setDisplayName(domainObject.getDisplayName());
    contributorEntity.setEmails(domainObject.getEmailAddresses());
    contributorEntity.setNames(domainObject.getNames());
    return contributorEntity;
  }
}

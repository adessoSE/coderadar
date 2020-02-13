package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.MergeContributorsPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import org.springframework.stereotype.Service;

@Service
public class MergeContributorsAdapter implements MergeContributorsPort {
  private final ContributorRepository contributorRepository;

  private final ContributorMapper mapper = new ContributorMapper();

  public MergeContributorsAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public void mergeContributors(Contributor c1, Contributor c2) {
    ContributorEntity firstEntity = mapper.mapDomainObject(c1);
    ContributorEntity secondEntity = mapper.mapDomainObject(c2);

    firstEntity.getEmails().addAll(secondEntity.getEmails());
    // firstEntity.getProjects().addAll(secondEntity.getProjects());
    // firstEntity.getFiles().addAll(secondEntity.getFiles());
    contributorRepository.save(firstEntity);
    contributorRepository.deleteById(secondEntity.getId());
  }
}

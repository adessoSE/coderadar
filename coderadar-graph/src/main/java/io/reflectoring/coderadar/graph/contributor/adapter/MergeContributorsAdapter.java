package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.contributor.port.driven.MergeContributorsPort;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MergeContributorsAdapter implements MergeContributorsPort {
  private final ContributorRepository contributorRepository;

  public MergeContributorsAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public void mergeContributors(long firstId, long secondId, String displayName) {
    Optional<ContributorEntity> firstOptional = contributorRepository.findById(firstId);
    Optional<ContributorEntity> secondOptional = contributorRepository.findById(secondId);
    if (firstOptional.isEmpty()) {
      throw new ContributorNotFoundException(firstId);
    }
    if (secondOptional.isEmpty()) {
      throw new ContributorNotFoundException(secondId);
    }

    ContributorEntity firstEntity = firstOptional.get();
    ContributorEntity secondEntity = secondOptional.get();

    firstEntity.setDisplayName(displayName);
    firstEntity.getNames().addAll(secondEntity.getNames());
    firstEntity.getEmails().addAll(secondEntity.getEmails());
    firstEntity.getProjects().addAll(secondEntity.getProjects());
    contributorRepository.save(firstEntity, 0);
    contributorRepository.deleteById(secondEntity.getId());
  }
}

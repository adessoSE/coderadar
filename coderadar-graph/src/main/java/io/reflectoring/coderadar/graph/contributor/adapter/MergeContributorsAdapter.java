package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.contributor.port.driven.MergeContributorsPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MergeContributorsAdapter implements MergeContributorsPort {
  private final ContributorRepository contributorRepository;

  private final ContributorMapper mapper = new ContributorMapper();

  public MergeContributorsAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public void mergeContributors(Long firstId, Long secondId) {
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

    firstEntity.getEmails().addAll(secondEntity.getEmails());
    firstEntity.getProjects().addAll(secondEntity.getProjects());
    firstEntity.getFiles().addAll(secondEntity.getFiles());
    contributorRepository.save(firstEntity);
    contributorRepository.deleteById(secondEntity.getId());
  }
}

package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.GetContributorPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GetContributorAdapter implements GetContributorPort {
  private final ContributorRepository contributorRepository;

  public GetContributorAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public Contributor getContributorById(Long id) {
    Optional<ContributorEntity> entity = contributorRepository.findById(id);
    if (!entity.isPresent()) {
      throw new ContributorNotFoundException("Contributor with id " + id + " not found.");
    }
    return new ContributorMapper().mapNodeEntity(entity.get());
  }
}

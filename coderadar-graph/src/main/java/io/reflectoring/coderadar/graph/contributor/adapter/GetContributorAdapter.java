package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.GetContributorPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import org.springframework.stereotype.Service;

@Service
public class GetContributorAdapter implements GetContributorPort {
  private final ContributorRepository contributorRepository;
  private final ContributorMapper mapper = new ContributorMapper();

  public GetContributorAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public Contributor get(long id) {
    ContributorEntity entity =
        contributorRepository.findById(id).orElseThrow(() -> new ContributorNotFoundException(id));
    return mapper.mapGraphObject(entity);
  }
}

package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.contributor.port.driven.UpdateContributorPort;
import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorCommand;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateContributorAdapter implements UpdateContributorPort {
  private final ContributorRepository contributorRepository;

  public UpdateContributorAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public void updateContributor(long id, UpdateContributorCommand command) {
    ContributorEntity contributor =
        contributorRepository.findById(id).orElseThrow(() -> new ContributorNotFoundException(id));
    contributor.setDisplayName(command.getDisplayName());
    contributorRepository.save(contributor, 0);
  }
}

package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.contributor.port.driven.UpdateContributorPort;
import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorCommand;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UpdateContributorAdapter implements UpdateContributorPort {
  private final ContributorRepository contributorRepository;

  public UpdateContributorAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public void updateContributor(Long id, UpdateContributorCommand command) {
    Optional<ContributorEntity> optionalContributorEntity = contributorRepository.findById(id);
    if (optionalContributorEntity.isEmpty()) {
      throw new ContributorNotFoundException(id);
    }
    ContributorEntity contributor = optionalContributorEntity.get();
    contributor.setDisplayName(command.getDisplayName());
    contributorRepository.save(contributor, 0);
  }
}

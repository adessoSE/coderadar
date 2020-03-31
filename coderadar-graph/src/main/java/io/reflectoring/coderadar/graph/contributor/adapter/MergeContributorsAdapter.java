package io.reflectoring.coderadar.graph.contributor.adapter;

import com.google.common.collect.Ordering;
import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.contributor.port.driven.MergeContributorsPort;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MergeContributorsAdapter implements MergeContributorsPort {
  private final ContributorRepository contributorRepository;
  private final Logger logger = LoggerFactory.getLogger(MergeContributorsAdapter.class);

  public MergeContributorsAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public void mergeContributors(List<Long> contributorIds, String displayName) {
    List<ContributorEntity> contributors =
        contributorRepository.findAllByIdsWithProjects(contributorIds);
    contributors.sort(Ordering.explicit(contributorIds).onResultOf(ContributorEntity::getId));
    contributorIds.forEach(
        id -> {
          if (contributors.stream().noneMatch(c -> c.getId().equals(id))) {
            throw new ContributorNotFoundException(id);
          }
        });
    ContributorEntity firstEntity = contributors.get(0);
    for (int i = 1; i < contributors.size(); i++) {
      ContributorEntity contributorEntity = contributors.get(i);
      firstEntity.getNames().addAll(contributorEntity.getNames());
      firstEntity.getEmails().addAll(contributorEntity.getEmails());
      firstEntity.getProjects().addAll(contributorEntity.getProjects());
      contributorRepository.deleteById(contributorEntity.getId());
      logger.info("Deleted contributor with id {}", contributorEntity.getId());
    }
    firstEntity.setDisplayName(displayName);
    contributorRepository.save(firstEntity, 1);
  }
}

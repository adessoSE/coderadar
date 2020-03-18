package io.reflectoring.coderadar.graph.contributor.adapter;

import com.google.common.collect.Ordering;
import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.contributor.port.driven.MergeContributorsPort;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MergeContributorsAdapter implements MergeContributorsPort {
  private final ContributorRepository contributorRepository;

  public MergeContributorsAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public void mergeContributors(List<Long> contributorIds, String displayName) {
    List<ContributorEntity> contributors = contributorRepository.findAllByIds(contributorIds);
    contributors.sort(Ordering.explicit(contributorIds).onResultOf(ContributorEntity::getId));
    contributorIds.forEach(
        id -> {
          if (contributors.stream().noneMatch(c -> c.getId().equals(id))) {
            throw new ContributorNotFoundException(id);
          }
        });
    ContributorEntity firstEntity = contributors.get(0);
    for (int i = 1; i < contributors.size(); i++) {
      ContributorEntity secondEntity = contributors.get(i);
      firstEntity.getNames().addAll(secondEntity.getNames());
      firstEntity.getEmails().addAll(secondEntity.getEmails());
      firstEntity.getProjects().addAll(secondEntity.getProjects());
      contributorRepository.deleteById(secondEntity.getId());
    }
    firstEntity.setDisplayName(displayName);
    contributorRepository.save(firstEntity, 1);
  }
}

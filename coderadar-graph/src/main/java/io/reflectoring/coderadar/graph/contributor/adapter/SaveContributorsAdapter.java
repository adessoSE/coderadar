package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.SaveContributorsPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaveContributorsAdapter implements SaveContributorsPort {
  private final ContributorRepository contributorRepository;

  public SaveContributorsAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public void save(List<Contributor> contributors) {
    ContributorMapper mapper = new ContributorMapper();
    List<ContributorEntity> contributorEntities =
        new ArrayList<>(mapper.mapDomainObjects(contributors));
    ContributorEntity first = contributorRepository.save(contributorEntities.get(0));
    Iterable<ContributorEntity> contributorEntityIterable =
        contributorRepository.saveAll(contributorEntities);
  }
}

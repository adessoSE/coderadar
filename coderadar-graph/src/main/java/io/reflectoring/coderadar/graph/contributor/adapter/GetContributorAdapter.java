package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.GetContributorPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GetContributorAdapter implements GetContributorPort {
  private final ContributorRepository contributorRepository;
  private final ContributorMapper mapper = new ContributorMapper();

  public GetContributorAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public Contributor findById(Long id) {
    Optional<ContributorEntity> entity = contributorRepository.findById(id);
    if (entity.isEmpty()) {
      throw new ContributorNotFoundException(id);
    }
    return mapper.mapNodeEntity(entity.get());
  }

  @Override
  public List<Contributor> findAll() {
    return mapper.mapNodeEntities(contributorRepository.findAll());
  }

  @Override
  public List<Contributor> findAllByProjectId(Long projectId) {
    return mapper.mapNodeEntities(contributorRepository.findAllByProjectId(projectId));
  }

  @Override
  public List<Contributor> findAllByProjectIdAndFilename(Long projectId, String filename) {
    return mapper.mapNodeEntities(
        contributorRepository.findAllByProjectIdAndFilename(projectId, filename));
  }
}

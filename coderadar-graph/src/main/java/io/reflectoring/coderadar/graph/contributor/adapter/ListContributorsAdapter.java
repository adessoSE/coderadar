package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ListContributorsPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListContributorsAdapter implements ListContributorsPort {
  private final ContributorMapper mapper = new ContributorMapper();
  private final ContributorRepository contributorRepository;

  public ListContributorsAdapter(ContributorRepository contributorRepository) {
    this.contributorRepository = contributorRepository;
  }

  @Override
  public List<Contributor> listAll() {
    return mapper.mapNodeEntities(contributorRepository.findAll());
  }

  @Override
  public List<Contributor> listAllByProjectId(long projectId) {
    return mapper.mapNodeEntities(contributorRepository.findAllByProjectId(projectId));
  }

  @Override
  public List<Contributor> listAllByProjectIdAndFilename(long projectId, String filename) {
    return mapper.mapNodeEntities(
        contributorRepository.findAllByProjectIdAndFilename(projectId, filename));
  }
}

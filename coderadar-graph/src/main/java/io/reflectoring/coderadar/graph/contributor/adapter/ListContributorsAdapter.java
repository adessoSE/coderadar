package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ListContributorsPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.graph.query.repository.ContributorQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListContributorsAdapter implements ListContributorsPort {
  private final ContributorMapper mapper = new ContributorMapper();
  private final ContributorRepository contributorRepository;
  private final ContributorQueryRepository contributorQueryRepository;

  public ListContributorsAdapter(
      ContributorRepository contributorRepository,
      ContributorQueryRepository contributorQueryRepository) {
    this.contributorRepository = contributorRepository;
    this.contributorQueryRepository = contributorQueryRepository;
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
  public List<Contributor> listAllByProjectIdAndFilepathInCommit(
      long projectId, String commitHash, String filename) {
    return mapper.mapNodeEntities(
        contributorQueryRepository.findAllByProjectIdAndFilepathInCommit(
            projectId, commitHash, filename));
  }
}

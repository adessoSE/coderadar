package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ListContributorsPort;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.graph.query.repository.ContributorQueryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListContributorsAdapter implements ListContributorsPort {
  private final ContributorMapper mapper = new ContributorMapper();
  private final ContributorRepository contributorRepository;
  private final FileRepository fileRepository;
  private final ContributorQueryRepository contributorQueryRepository;

  public ListContributorsAdapter(
      ContributorRepository contributorRepository,
      FileRepository fileRepository,
      ContributorQueryRepository contributorQueryRepository) {
    this.contributorRepository = contributorRepository;
    this.fileRepository = fileRepository;
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
  public List<Contributor> listAllByProjectIdAndPathInCommit(
      long projectId, String commitHash, String path) {
    if (fileRepository.fileExistsByPath(projectId, path)) {
      return mapper.mapNodeEntities(
          contributorQueryRepository.findAllByProjectIdAndFilepathInCommit(
              projectId, commitHash, path));
    } else {
      return mapper.mapNodeEntities(
          contributorQueryRepository.findAllByProjectIdAndModulePathInCommit(
              projectId, commitHash, path));
    }
  }
}

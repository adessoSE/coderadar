package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.port.driven.ListContributorsPort;
import io.reflectoring.coderadar.domain.Contributor;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.graph.query.repository.ContributorQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListContributorsAdapter implements ListContributorsPort {

  private final ContributorRepository contributorRepository;
  private final FileRepository fileRepository;
  private final ContributorQueryRepository contributorQueryRepository;

  private final ContributorMapper mapper = new ContributorMapper();

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
      long projectId, long commitHash, String path) {
    if (fileRepository.fileWithPathExists(projectId, path)) {
      return mapper.mapNodeEntities(
          contributorQueryRepository.findAllByProjectIdAndFilepathInCommit(
              projectId, commitHash, path));
    } else {
      return mapper.mapNodeEntities(
          contributorQueryRepository.findAllByProjectIdAndDirectoryInCommit(
              projectId, commitHash, path));
    }
  }
}

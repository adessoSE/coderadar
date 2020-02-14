package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.SaveContributorsPort;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SaveContributorsAdapter implements SaveContributorsPort {
  private final ContributorRepository contributorRepository;
  private final ProjectRepository projectRepository;
  private final CommitRepository commitRepository;

  public SaveContributorsAdapter(
      ContributorRepository contributorRepository,
      ProjectRepository projectRepository,
      CommitRepository commitRepository) {
    this.contributorRepository = contributorRepository;
    this.projectRepository = projectRepository;
    this.commitRepository = commitRepository;
  }

  @Override
  public List<Contributor> save(List<Contributor> contributors, Long projectId) {
    ProjectEntity projectEntity = projectRepository.findById(projectId).get(); // project must exist

    List<ContributorEntity> contributorEntities =
        new ArrayList<>(new ContributorMapper().mapDomainObjects(contributors));
    for (ContributorEntity entity : contributorEntities) {
      entity.getProjects().add(projectEntity);
      for (String commitHash : entity.getCommitHashes()) {
        Optional<CommitEntity> optionalCommit =
            commitRepository.findByNameAndProjectId(commitHash, projectId);
        optionalCommit.ifPresent(commitEntity -> entity.getCommits().add(commitEntity));
      }
    }
    return new ArrayList<>(
        new ContributorMapper()
            .mapNodeEntities(contributorRepository.saveAll(contributorEntities)));
  }
}

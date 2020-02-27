package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.SaveContributorsPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaveContributorsAdapter implements SaveContributorsPort {
  private final ContributorRepository contributorRepository;
  private final ProjectRepository projectRepository;

  public SaveContributorsAdapter(
      ContributorRepository contributorRepository, ProjectRepository projectRepository) {
    this.contributorRepository = contributorRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public List<Contributor> save(List<Contributor> contributors, Long projectId) {
    ProjectEntity projectEntity = projectRepository.findById(projectId).get(); // project must exist

    List<ContributorEntity> contributorEntities =
        new ArrayList<>(new ContributorMapper().mapDomainObjects(contributors));
    for (ContributorEntity entity : contributorEntities) {
      entity.getProjects().add(projectEntity);
    }
    contributorRepository.save(contributorEntities, 1);
    return new ArrayList<>(new ContributorMapper().mapNodeEntities(contributorEntities));
  }
}

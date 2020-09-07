package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.SaveContributorsPort;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveContributorsAdapter implements SaveContributorsPort {
  private final ContributorRepository contributorRepository;
  private final ProjectRepository projectRepository;
  private final ContributorMapper contributorMapper = new ContributorMapper();

  @Override
  public void save(List<Contributor> contributors, long projectId) {
    ProjectEntity projectEntity =
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));

    List<ContributorEntity> contributorEntities =
        new ArrayList<>(contributorMapper.mapDomainObjects(contributors));
    for (ContributorEntity entity : contributorEntities) {
      entity.getProjects().add(projectEntity);
    }
    contributorRepository.save(contributorEntities, 1);
  }
}

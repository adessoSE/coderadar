package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Commit;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GetCommitsInProjectServiceNeo4j")
public class GetCommitsInProjectService implements GetCommitsInProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final GetCommitsInProjectRepository getCommitsInProjectRepository;

  @Autowired
  public GetCommitsInProjectService(
      GetProjectRepository getProjectRepository,
      GetCommitsInProjectRepository getCommitsInProjectRepository) {
    this.getProjectRepository = getProjectRepository;
    this.getCommitsInProjectRepository = getCommitsInProjectRepository;
  }

  @Override
  public List<Commit> get(Long projectId) {
    Optional<Project> persistedProject = getProjectRepository.findById(projectId);

    if (persistedProject.isPresent()) {
      return null;
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

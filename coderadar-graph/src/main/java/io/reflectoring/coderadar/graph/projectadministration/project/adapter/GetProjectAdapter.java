package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GetProjectAdapter implements GetProjectPort {
  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper = new ProjectMapper();

  public GetProjectAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public Project get(long id) {
    Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
    if (projectEntity.isPresent()) {
      return projectMapper.mapGraphObject(projectEntity.get());
    } else {
      throw new ProjectNotFoundException(id);
    }
  }

  @Override
  public Project get(String name) {
    Optional<ProjectEntity> projectEntity = projectRepository.findByName(name);
    if (projectEntity.isPresent()) {
      return projectMapper.mapGraphObject(projectEntity.get());
    } else {
      throw new ProjectNotFoundException(name);
    }
  }

  @Override
  public boolean existsByName(String name) {
    return projectRepository.existsByName(name);
  }

  @Override
  public boolean existsById(long projectId) {
    return projectRepository.existsById(projectId);
  }
}

package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProjectAdapter implements GetProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final ProjectMapper projectMapper = new ProjectMapper();

  @Autowired
  public GetProjectAdapter(GetProjectRepository getProjectRepository) {
    this.getProjectRepository = getProjectRepository;
  }

  @Override
  public Project get(Long id) throws ProjectNotFoundException {
    Optional<ProjectEntity> projectEntity = getProjectRepository.findById(id);
    if (projectEntity.isPresent()) {
      return projectMapper.mapNodeEntity(projectEntity.get());
    } else {
      throw new ProjectNotFoundException(id);
    }
  }

  @Override
  public Project get(String name) throws ProjectNotFoundException {
    Optional<ProjectEntity> projectEntity = getProjectRepository.findByName(name);
    if (projectEntity.isPresent()) {
      return projectMapper.mapNodeEntity(projectEntity.get());
    } else {
      throw new ProjectNotFoundException(name);
    }
  }

  @Override
  public boolean existsByName(String name) {
    return getProjectRepository.findByName(name).isPresent();
  }

  @Override
  public boolean existsById(Long projectId) {
    return getProjectRepository.existsById(projectId);
  }
}

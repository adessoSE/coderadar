package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProjectAdapter implements GetProjectPort {
  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper = new ProjectMapper();

  @Autowired
  public GetProjectAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public Project get(Long id) throws ProjectNotFoundException {
    Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
    if (projectEntity.isPresent()) {
      return projectMapper.mapNodeEntity(projectEntity.get());
    } else {
      throw new ProjectNotFoundException(id);
    }
  }

  @Override
  public Project get(String name) throws ProjectNotFoundException {
    Optional<ProjectEntity> projectEntity = projectRepository.findByName(name);
    if (projectEntity.isPresent()) {
      return projectMapper.mapNodeEntity(projectEntity.get());
    } else {
      throw new ProjectNotFoundException(name);
    }
  }

  @Override
  public boolean existsByName(String name) {
    return projectRepository.findByName(name).isPresent();
  }

  @Override
  public boolean existsById(Long projectId) {
    return projectRepository.existsById(projectId);
  }

  @Override
  public List<Project> findByName(String name) {
    return new ArrayList<>(projectMapper.mapNodeEntities(projectRepository.findAllByName(name)));
  }
}

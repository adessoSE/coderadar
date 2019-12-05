package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
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
  public Project get(Long id) {
    Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
    if (projectEntity.isPresent()) {
      return projectMapper.mapNodeEntity(projectEntity.get());
    } else {
      throw new ProjectNotFoundException(id);
    }
  }

  @Override
  public Project get(String name) {
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
  public GetProjectResponse getProjectResponse(Long id) {
    Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
    if (projectEntity.isPresent()) {
      GetProjectResponse response = new GetProjectResponse();
      response.setId(projectEntity.get().getId());
      response.setName(projectEntity.get().getName());
      response.setVcsUsername(projectEntity.get().getVcsUsername());
      response.setVcsPassword(projectEntity.get().getVcsPassword());
      response.setVcsOnline(projectEntity.get().isVcsOnline());
      response.setVcsUrl(projectEntity.get().getVcsUrl());
      response.setStartDate(projectEntity.get().getVcsStart());
      response.setEndDate(projectEntity.get().getVcsEnd());
      return response;
    } else {
      throw new ProjectNotFoundException(id);
    }
  }
}

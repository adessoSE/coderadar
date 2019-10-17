package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListProjectsAdapter implements ListProjectsPort {
  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper = new ProjectMapper();

  @Autowired
  public ListProjectsAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public Iterable<Project> getProjects() {
    return projectMapper.mapNodeEntities(projectRepository.findAllProjects());
  }

  @Override
  public List<GetProjectResponse> getProjectResponses() {
    List<GetProjectResponse> responses = new ArrayList<>();
    for (ProjectEntity projectEntity : projectRepository.findAllProjects()) {
      GetProjectResponse response = new GetProjectResponse();
      response.setName(projectEntity.getName());
      response.setId(projectEntity.getId());
      response.setEndDate(projectEntity.getVcsEnd());
      response.setStartDate(projectEntity.getVcsStart());
      response.setVcsOnline(projectEntity.isVcsOnline());
      response.setVcsPassword(projectEntity.getVcsPassword());
      response.setVcsUsername(projectEntity.getVcsUsername());
      response.setVcsUrl(projectEntity.getVcsUrl());
      responses.add(response);
    }
    return responses;
  }
}

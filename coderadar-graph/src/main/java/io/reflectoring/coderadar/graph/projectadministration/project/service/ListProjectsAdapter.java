package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ListProjectsRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListProjectsAdapter implements ListProjectsPort {
  private final ListProjectsRepository listProjectsRepository;
  private final ProjectMapper projectMapper = new ProjectMapper();

  @Autowired
  public ListProjectsAdapter(ListProjectsRepository listProjectsRepository) {
    this.listProjectsRepository = listProjectsRepository;
  }

  @Override
  public Iterable<Project> getProjects() {
    return projectMapper.mapNodeEntities(listProjectsRepository.findAll());
  }
}

package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.ListProjectsRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ListProjectsServiceNeo4j")
public class ListProjectsService implements ListProjectsPort {
  private final ListProjectsRepository listProjectsRepository;

  @Autowired
  public ListProjectsService(ListProjectsRepository listProjectsRepository) {
    this.listProjectsRepository = listProjectsRepository;
  }

  @Override
  public Iterable<Project> getProjects() {
    return listProjectsRepository.findAll();
  }
}

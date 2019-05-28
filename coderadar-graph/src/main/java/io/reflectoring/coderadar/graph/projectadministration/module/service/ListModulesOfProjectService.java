package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ListModulesOfProjectServiceNeo4j")
public class ListModulesOfProjectService implements ListModulesOfProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final ListModulesOfProjectRepository listModulesOfProjectRepository;

  @Autowired
  public ListModulesOfProjectService(
      GetProjectRepository getProjectRepository,
      ListModulesOfProjectRepository listModulesOfProjectRepository) {
    this.getProjectRepository = getProjectRepository;
    this.listModulesOfProjectRepository = listModulesOfProjectRepository;
  }

  @Override
  public List<Module> listModules(Long projectId) {
    Optional<Project> project = getProjectRepository.findById(projectId);
    if (project.isPresent()) {
      return listModulesOfProjectRepository.findByProjectId(projectId);
    } else {
      throw new ProjectNotFoundException("No modules can be listed from a non-existing project.");
    }
  }
}

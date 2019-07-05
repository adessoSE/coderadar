package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListModulesOfProjectAdapter implements ListModulesOfProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final ListModulesOfProjectRepository listModulesOfProjectRepository;
  private final ModuleMapper moduleMapper = new ModuleMapper();

  @Autowired
  public ListModulesOfProjectAdapter(
      GetProjectRepository getProjectRepository,
      ListModulesOfProjectRepository listModulesOfProjectRepository) {
    this.getProjectRepository = getProjectRepository;
    this.listModulesOfProjectRepository = listModulesOfProjectRepository;
  }

  @Override
  public Collection<Module> listModules(Long projectId) {
    Optional<ProjectEntity> project = getProjectRepository.findById(projectId);
    if (project.isPresent()) {
      return moduleMapper.mapNodeEntities(
          listModulesOfProjectRepository.findModulesInProject(projectId));
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

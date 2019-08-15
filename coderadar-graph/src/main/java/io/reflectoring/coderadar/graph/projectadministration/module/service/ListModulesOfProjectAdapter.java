package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListModulesOfProjectAdapter implements ListModulesOfProjectPort {
  private final ProjectRepository projectRepository;
  private final ModuleRepository moduleRepository;
  private final ModuleMapper moduleMapper = new ModuleMapper();

  @Autowired
  public ListModulesOfProjectAdapter(
      ProjectRepository projectRepository, ModuleRepository moduleRepository) {
    this.projectRepository = projectRepository;
    this.moduleRepository = moduleRepository;
  }

  @Override
  public Collection<Module> listModules(Long projectId) {
    Optional<ProjectEntity> project = projectRepository.findById(projectId);
    if (project.isPresent()) {
      return moduleMapper.mapNodeEntities(moduleRepository.findModulesInProject(projectId));
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

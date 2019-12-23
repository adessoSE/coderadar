package io.reflectoring.coderadar.graph.projectadministration.module.adapter;

import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListModulesOfProjectAdapter implements ListModulesOfProjectPort {
  private final ProjectRepository projectRepository;
  private final ModuleRepository moduleRepository;
  private final ModuleMapper moduleMapper = new ModuleMapper();

  public ListModulesOfProjectAdapter(
      ProjectRepository projectRepository, ModuleRepository moduleRepository) {
    this.projectRepository = projectRepository;
    this.moduleRepository = moduleRepository;
  }

  @Override
  public List<Module> listModules(Long projectId) {
    if (projectRepository.existsById(projectId)) {
      return moduleMapper.mapNodeEntities(moduleRepository.findModulesInProject(projectId));
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

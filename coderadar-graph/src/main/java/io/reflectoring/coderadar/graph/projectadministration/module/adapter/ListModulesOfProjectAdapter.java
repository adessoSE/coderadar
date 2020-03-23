package io.reflectoring.coderadar.graph.projectadministration.module.adapter;

import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListModulesOfProjectAdapter implements ListModulesOfProjectPort {
  private final ModuleRepository moduleRepository;
  private final ModuleMapper moduleMapper = new ModuleMapper();

  public ListModulesOfProjectAdapter(ModuleRepository moduleRepository) {
    this.moduleRepository = moduleRepository;
  }

  @Override
  public List<Module> listModules(long projectId) {
    return moduleMapper.mapNodeEntities(moduleRepository.findModulesInProjectSortedDesc(projectId));
  }
}

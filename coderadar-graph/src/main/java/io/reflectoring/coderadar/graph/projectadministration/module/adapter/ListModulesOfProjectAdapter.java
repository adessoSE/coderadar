package io.reflectoring.coderadar.graph.projectadministration.module.adapter;

import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListModulesOfProjectAdapter implements ListModulesOfProjectPort {
  private final ModuleRepository moduleRepository;
  private final ModuleMapper moduleMapper = new ModuleMapper();

  public ListModulesOfProjectAdapter(ModuleRepository moduleRepository) {
    this.moduleRepository = moduleRepository;
  }

  @Override
  public List<Module> listModules(Long projectId) {
    return moduleMapper.mapNodeEntities(moduleRepository.findModulesInProjectSortedDesc(projectId));
  }
}

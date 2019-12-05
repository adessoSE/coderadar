package io.reflectoring.coderadar.graph.projectadministration.module.adapter;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GetModuleAdapter implements GetModulePort {
  private final ModuleRepository moduleRepository;
  private final ModuleMapper moduleMapper = new ModuleMapper();

  public GetModuleAdapter(ModuleRepository moduleRepository) {
    this.moduleRepository = moduleRepository;
  }

  @Override
  public Module get(Long id) {
    Optional<ModuleEntity> moduleEntity = moduleRepository.findById(id);
    if (moduleEntity.isPresent()) {
      return moduleMapper.mapNodeEntity(moduleEntity.get());
    } else {
      throw new ModuleNotFoundException(id);
    }
  }
}

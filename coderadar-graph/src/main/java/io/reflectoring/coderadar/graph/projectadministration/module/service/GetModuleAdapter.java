package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetModuleAdapter implements GetModulePort {
  private final GetModuleRepository getModuleRepository;
  private final ModuleMapper moduleMapper = new ModuleMapper();

  @Autowired
  public GetModuleAdapter(GetModuleRepository getModuleRepository) {
    this.getModuleRepository = getModuleRepository;
  }

  @Override
  public Module get(Long id) {
    Optional<ModuleEntity> moduleEntity = getModuleRepository.findById(id);
    if (moduleEntity.isPresent()) {
      return moduleMapper.mapNodeEntity(moduleEntity.get());
    } else {
      throw new ModuleNotFoundException(id);
    }
  }
}

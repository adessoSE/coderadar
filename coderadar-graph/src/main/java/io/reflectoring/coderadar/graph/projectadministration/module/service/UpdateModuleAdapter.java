package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.UpdateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.UpdateModulePort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateModuleAdapter implements UpdateModulePort {
  private final GetModuleRepository getModuleRepository;
  private final UpdateModuleRepository updateModuleRepository;
  private final ProjectMapper projectMapper = new ProjectMapper();

  @Autowired
  public UpdateModuleAdapter(
      GetModuleRepository getModuleRepository, UpdateModuleRepository updateModuleRepository) {
    this.getModuleRepository = getModuleRepository;
    this.updateModuleRepository = updateModuleRepository;
  }

  @Override
  public void updateModule(Module module) throws ModuleNotFoundException {
    Optional<ModuleEntity> moduleEntity = getModuleRepository.findById(module.getId());
    if (moduleEntity.isPresent()) {
      moduleEntity.get().setPath(module.getPath());
      updateModuleRepository.save(moduleEntity.get());
    } else {
      throw new ModuleNotFoundException(module.getId());
    }
  }
}

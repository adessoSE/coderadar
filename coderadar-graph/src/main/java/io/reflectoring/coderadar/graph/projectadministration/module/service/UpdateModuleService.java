package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.core.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.UpdateModulePort;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.UpdateModuleRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateModuleService implements UpdateModulePort {
  private final GetModuleRepository getModuleRepository;
  private final UpdateModuleRepository updateModuleRepository;

  @Autowired
  public UpdateModuleService(
      GetModuleRepository getModuleRepository, UpdateModuleRepository updateModuleRepository) {
    this.getModuleRepository = getModuleRepository;
    this.updateModuleRepository = updateModuleRepository;
  }

  @Override
  public void updateModule(Module module) {
    Optional<Module> oldModule = getModuleRepository.findById(module.getId());

    if (oldModule.isPresent()) {
      updateModuleRepository.save(module);
    } else {
      throw new ModuleNotFoundException(
          String.format("There is no module with the ID %d. Updating failed.", module.getId()));
    }
  }
}

package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.module.repository.DeleteModuleRepository;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteModuleAdapter implements DeleteModulePort {
  private final DeleteModuleRepository deleteModuleRepository;

  @Autowired
  public DeleteModuleAdapter(DeleteModuleRepository deleteModuleRepository) {
    this.deleteModuleRepository = deleteModuleRepository;
  }

  @Override
  public void delete(Long id) throws ModuleNotFoundException {
    deleteModuleRepository.findById(id).orElseThrow(() -> new ModuleNotFoundException(id));
    deleteModuleRepository.deleteById(id);
  }

  @Override
  public void delete(Module module) throws ModuleNotFoundException {
    deleteModuleRepository
        .findById(module.getId())
        .orElseThrow(() -> new ModuleNotFoundException(module.getId()));
    deleteModuleRepository.deleteById(module.getId());
  }
}

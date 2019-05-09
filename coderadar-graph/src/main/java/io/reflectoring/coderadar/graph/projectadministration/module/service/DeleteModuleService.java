package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.DeleteModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DeleteModuleServiceNeo4j")
public class DeleteModuleService implements DeleteModulePort {
  private final DeleteModuleRepository deleteModuleRepository;
  private final DeleteProjectRepository deleteProjectRepository;

  @Autowired
  public DeleteModuleService(
      DeleteModuleRepository deleteModuleRepository,
      DeleteProjectRepository deleteProjectRepository) {
    this.deleteModuleRepository = deleteModuleRepository;
    this.deleteProjectRepository = deleteProjectRepository;
  }

  @Override
  public void delete(Long id) {
    deleteModuleRepository.deleteById(id);
  }

  @Override
  public void delete(Module module) {
    deleteModuleRepository.delete(module);
  }
}

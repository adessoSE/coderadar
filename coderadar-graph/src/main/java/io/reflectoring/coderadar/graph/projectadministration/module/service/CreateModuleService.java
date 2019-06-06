package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CreateModuleServiceNeo4j")
public class CreateModuleService implements CreateModulePort {
  private final CreateModuleRepository createModuleRepository;

  @Autowired
  public CreateModuleService(CreateModuleRepository createModuleRepository) {
    this.createModuleRepository = createModuleRepository;
  }

  @Override
  public Long createModule(Module module) {
    return createModuleRepository.save(module).getId();
  }
}

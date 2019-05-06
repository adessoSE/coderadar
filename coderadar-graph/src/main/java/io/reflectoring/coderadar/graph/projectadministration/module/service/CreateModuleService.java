package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

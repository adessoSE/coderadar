package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.graph.exception.InvalidArgumentException;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateModuleService implements CreateModulePort {
  private final GetProjectRepository getProjectRepository;
  private final CreateModuleRepository createModuleRepository;

  @Autowired
  public CreateModuleService(
      GetProjectRepository getProjectRepository, CreateModuleRepository createModuleRepository) {
    this.getProjectRepository = getProjectRepository;
    this.createModuleRepository = createModuleRepository;
  }

  @Override
  public Long createModule(Long projectId, Module module) {
    Optional<Project> project = getProjectRepository.findById(1L);

    if (project.isPresent()) {
      module.setProject(project.get());
      return createModuleRepository.save(module).getId();
    } else {
      throw new ProjectNotFoundException(
          String.format(
              "There is no project with the ID %d. Creation of module failed.", projectId));
    }
  }
}

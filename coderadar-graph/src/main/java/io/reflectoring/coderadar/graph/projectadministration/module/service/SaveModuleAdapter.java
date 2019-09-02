package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.SaveModulePort;
import org.springframework.stereotype.Service;

@Service
public class SaveModuleAdapter implements SaveModulePort {

  private final ProjectRepository projectRepository;
  private final ModuleRepository moduleRepository;

  public SaveModuleAdapter(ProjectRepository projectRepository, ModuleRepository moduleRepository) {
    this.projectRepository = projectRepository;
    this.moduleRepository = moduleRepository;
  }

  @Override
  public Long saveModule(Module module, Long projectId)
      throws ModuleAlreadyExistsException, ModulePathInvalidException,
          ProjectIsBeingProcessedException {

    ModuleEntity moduleEntity = new ModuleMapper().mapDomainObject(module);
    ProjectEntity projectEntity =
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));

    if (projectEntity.isBeingProcessed()) {
      throw new ProjectIsBeingProcessedException(projectId);
    }
    checkPathIsValid(moduleEntity, projectEntity);
    return moduleRepository.save(moduleEntity).getId();
  }

  /**
   * Checks for errors in the path and adds/removes backslashes where necessary
   *
   * @param moduleEntity The entity to perform checks on.
   * @param projectEntity The project to check the paths of
   */
  private void checkPathIsValid(ModuleEntity moduleEntity, ProjectEntity projectEntity)
      throws ModulePathInvalidException, ModuleAlreadyExistsException {

    moduleEntity.setPath(moduleEntity.getPath().trim());
    if (moduleEntity.getPath().contains("//")) {
      throw new ModulePathInvalidException(moduleEntity.getPath());
    }
    // Check for a slash at the start and end of the path.
    if (!moduleEntity.getPath().endsWith("/")) {
      moduleEntity.setPath(moduleEntity.getPath().concat("/"));
    }
    if (moduleEntity.getPath().startsWith("/")) {
      moduleEntity.setPath(moduleEntity.getPath().substring(1));
    }

    // Check if a module with the same path already exists.
    for (ModuleEntity entity : moduleRepository.findModulesInProject(projectEntity.getId())) {
      if (entity.getPath().equals(moduleEntity.getPath())) {
        throw new ModuleAlreadyExistsException(moduleEntity.getPath());
      }
    }
  }
}

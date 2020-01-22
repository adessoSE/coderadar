package io.reflectoring.coderadar.graph.projectadministration.module.adapter;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateModuleAdapter implements CreateModulePort {
  private final ModuleRepository moduleRepository;
  private final ProjectRepository projectRepository;

  public CreateModuleAdapter(
      ModuleRepository moduleRepository, ProjectRepository projectRepository) {
    this.moduleRepository = moduleRepository;
    this.projectRepository = projectRepository;
  }

  /**
   * Adds a new module to the project.
   *
   * @param modulePath The module path.
   * @param projectId The project id.
   * @throws ProjectNotFoundException Thrown if a project with projectId is not found.
   * @return The id of the newly created module
   */
  @Override
  public Long createModule(String modulePath, Long projectId) throws ModulePathInvalidException {
    ProjectEntity projectEntity =
        projectRepository
            .findByIdWithModules(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    ModuleEntity foundModule = findParentModuleInProject(projectEntity, modulePath);
    modulePath = checkPathIsValid(modulePath, projectEntity);
    if (foundModule != null) {
      return attachModuleToModule(foundModule, modulePath);
    } else {
      return attachModuleToProject(projectEntity, modulePath);
    }
  }

  /**
   * Attaches a child module to a parent module and moves all files with a matching path to the
   * child module.
   *
   * @param parentModule The parent module
   * @param childModulePath The child module path
   */
  private Long attachModuleToModule(ModuleEntity parentModule, String childModulePath) {
    ModuleEntity childModule;

    if (moduleRepository.fileInPathExists(childModulePath, parentModule.getId())) {
      childModule = moduleRepository.createModuleInModule(parentModule.getId(), childModulePath);
    } else {
      childModule = new ModuleEntity();
      childModule.setPath(childModulePath);
      childModule.setParentModule(parentModule);
      moduleRepository.save(childModule);
    }

    // Check to see if the module could a have child
    for (ModuleEntity child : findChildModules(parentModule.getChildModules(), childModulePath)) {
      child.setParentModule(childModule);
      childModule.getChildModules().add(child);
      parentModule.getChildModules().removeIf(entity -> entity.getId().equals(child.getId()));
      moduleRepository.save(child);
      moduleRepository.detachModuleFromModule(parentModule.getId(), child.getId());
    }
    return childModule.getId();
  }

  /**
   * Attaches a module directly to a project.
   *
   * @param projectEntity The project to attach the module to.
   * @param modulePath The module path.
   */
  private Long attachModuleToProject(ProjectEntity projectEntity, String modulePath) {
    ModuleEntity moduleEntity;

    if (moduleRepository.fileInPathExists(modulePath, projectEntity.getId())) {
      moduleEntity = moduleRepository.createModuleInProject(projectEntity.getId(), modulePath);
    } else {
      moduleEntity = new ModuleEntity();
      moduleEntity.setPath(modulePath);
      moduleEntity.setProject(projectEntity);
      moduleRepository.save(moduleEntity);
    }

    // Check to see if the module could a have child
    for (ModuleEntity childModule : findChildModules(projectEntity.getModules(), modulePath)) {
      childModule.setParentModule(moduleEntity);
      childModule.setProject(null);
      moduleEntity.getChildModules().add(childModule);
      moduleRepository.save(childModule);
      projectEntity.getModules().removeIf(entity -> entity.getId().equals(childModule.getId()));
      moduleRepository.detachModuleFromProject(projectEntity.getId(), childModule.getId());
    }
    return moduleEntity.getId();
  }

  /**
   * Finds a module in the project, that contains the given path
   *
   * @param projectEntity The project to check
   * @param modulePath The path to look for
   * @return The module entity that contains the path or null if nothing is found.
   */
  private ModuleEntity findParentModuleInProject(ProjectEntity projectEntity, String modulePath) {
    ModuleEntity foundModule;
    for (ModuleEntity m : projectEntity.getModules()) {
      foundModule = findModule(m, modulePath);
      if (foundModule != null) {
        return foundModule;
      }
    }
    return null;
  }

  /**
   * Finds a parent module for the given path. Checks all children of the given module.
   *
   * @param entity The module to look in.
   * @param path The path to check for.
   * @return The parent module or null if nothing is found.
   */
  private ModuleEntity findModule(ModuleEntity entity, String path) {
    long id = entity.getId();
    entity = moduleRepository.findById(id).orElseThrow(() -> new ModuleNotFoundException(id));
    if (path.startsWith(entity.getPath())) {
      if (entity.getChildModules().isEmpty()) {
        return entity;
      } else {
        for (ModuleEntity m : entity.getChildModules()) {
          ModuleEntity result = findModule(m, path);
          if (result != null) {
            return result;
          }
        }
        return entity;
      }
    } else {
      return null;
    }
  }

  /**
   * Finds possible children modules for the given path.
   *
   * @param modules a list of modules to check
   * @param path The new module path to check.
   * @return The children modules.
   */
  private List<ModuleEntity> findChildModules(List<ModuleEntity> modules, String path) {
    List<ModuleEntity> childModules = new ArrayList<>();
    for (ModuleEntity m : modules) {
      if (m.getPath().startsWith(path)) {
        childModules.add(
            moduleRepository
                .findById(m.getId())
                .orElseThrow(() -> new ModuleNotFoundException(m.getId())));
      }
    }
    return childModules;
  }

  /**
   * Checks for errors in the path and adds/removes backslashes where necessary
   *
   * @param path The path to perform checks on.
   * @param projectEntity The project to check the paths of
   * @return The corrected path.
   */
  private String checkPathIsValid(String path, ProjectEntity projectEntity)
      throws ModulePathInvalidException, ModuleAlreadyExistsException {

    path = path.trim();
    if (path.contains("//")) {
      throw new ModulePathInvalidException(path);
    }
    // Check for a slash at the start and end of the path.
    if (!path.endsWith("/")) {
      path = path.concat("/");
    }
    if (path.startsWith("/")) {
      path = path.substring(1);
    }

    // Check if a module with the same path already exists.
    for (ModuleEntity entity : moduleRepository.findModulesInProject(projectEntity.getId())) {
      if (entity.getPath().equals(path)) {
        throw new ModuleAlreadyExistsException(path);
      }
    }
    return path;
  }
}

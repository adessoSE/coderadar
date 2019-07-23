package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.ProjectStatusAdapter;
import io.reflectoring.coderadar.projectadministration.*;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class CreateModuleAdapter implements CreateModulePort {
  private final CreateModuleRepository createModuleRepository;
  private final GetProjectRepository getProjectRepository;
  private final ListModulesOfProjectRepository listModulesOfProjectRepository;
  private final TaskExecutor taskExecutor;
  private final ModuleMapper moduleMapper = new ModuleMapper();
  private final ProjectStatusAdapter projectStatusAdapter;

  @Autowired
  public CreateModuleAdapter(
      CreateModuleRepository createModuleRepository,
      GetProjectRepository getProjectRepository,
      ListModulesOfProjectRepository listModulesOfProjectRepository,
      TaskExecutor taskExecutor,
      ProjectStatusAdapter projectStatusAdapter) {
    this.createModuleRepository = createModuleRepository;
    this.getProjectRepository = getProjectRepository;
    this.listModulesOfProjectRepository = listModulesOfProjectRepository;
    this.taskExecutor = taskExecutor;
    this.projectStatusAdapter = projectStatusAdapter;
  }

  /**
   * Adds a new module to the project.
   *
   * @param module The module to add.
   * @param projectId The project id/
   * @return The id of the newly created module.
   * @throws ProjectNotFoundException Thrown if a project with projectId is not found.
   * @throws ModuleAlreadyExistsException Thrown if a module with the same name already exists.
   * @throws ModulePathInvalidException Thrown if the path is invalid
   * @throws ProjectIsBeingProcessedException Thrown if the project is being processed at the
   *     moment.
   */
  @Override
  public Long createModule(Module module, Long projectId)
      throws ModuleAlreadyExistsException, ModulePathInvalidException,
          ProjectIsBeingProcessedException {
    ModuleEntity moduleEntity = moduleMapper.mapDomainObject(module);
    ProjectEntity projectEntity =
        getProjectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));

    if (projectStatusAdapter.isBeingProcessed(projectId)) {
      throw new ProjectIsBeingProcessedException(projectId);
    }

    checkPathIsValid(moduleEntity, projectEntity);
    projectStatusAdapter.setBeingProcessed(projectId, true);

    Long moduleId = createModuleRepository.save(moduleEntity).getId();

    taskExecutor.execute(
        () -> {
          ModuleEntity foundModule =
              findParentModuleInProject(projectEntity, moduleEntity.getPath());
          if (foundModule != null) {
            attachModuleToModule(foundModule, moduleEntity);
          } else {
            attachModuleToProject(projectEntity, moduleEntity);
          }
          projectStatusAdapter.setBeingProcessed(projectId, false);
        });
    return moduleId;
  }

  /**
   * Attaches a child module to a parent module and moves all files with a matching path to the
   * child module.
   *
   * @param parentModule The parent module
   * @param childModule The child module
   */
  private void attachModuleToModule(ModuleEntity parentModule, ModuleEntity childModule) {
    for (FileEntity fileEntity : parentModule.getFiles()) {
      if (fileEntity.getPath().startsWith(childModule.getPath())) {
        childModule.getFiles().add(fileEntity);
        createModuleRepository.detachFileFromModule(parentModule.getId(), fileEntity.getId());
      }
    }
    parentModule.getFiles().removeAll(childModule.getFiles());
    // Check to see if the module could a have child
    for (ModuleEntity child :
        findChildModules(parentModule.getChildModules(), childModule.getPath())) {
      child.setParentModule(childModule);
      childModule.getChildModules().add(child);
      parentModule.getChildModules().removeIf(entity -> entity.getId().equals(child.getId()));
      createModuleRepository.save(child);
      createModuleRepository.detachModuleFromModule(parentModule.getId(), child.getId());
    }
    parentModule.getChildModules().add(childModule);
    createModuleRepository.save(parentModule);
    createModuleRepository.save(childModule);
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
    for (ModuleEntity entity :
        listModulesOfProjectRepository.findModulesInProject(projectEntity.getId())) {
      if (entity.getPath().equals(moduleEntity.getPath())) {
        throw new ModuleAlreadyExistsException(moduleEntity.getPath());
      }
    }
  }

  /**
   * Attaches a module directly to a project.
   *
   * @param projectEntity The project to attach the module to.
   * @param moduleEntity The module to attach.
   */
  private void attachModuleToProject(ProjectEntity projectEntity, ModuleEntity moduleEntity) {

    // Move all files contained in the module path from the project to the module.
    moduleEntity.setProject(projectEntity);
    for (FileEntity fileEntity : projectEntity.getFiles()) {
      if (fileEntity.getPath().startsWith(moduleEntity.getPath())) {
        moduleEntity.getFiles().add(fileEntity);
        createModuleRepository.detachFileFromProject(projectEntity.getId(), fileEntity.getId());
      }
    }
    projectEntity.getFiles().removeAll(moduleEntity.getFiles());

    // Check to see if the module could a have child
    for (ModuleEntity childModule :
        findChildModules(projectEntity.getModules(), moduleEntity.getPath())) {
      childModule.setParentModule(moduleEntity);
      childModule.setProject(null);
      moduleEntity.getChildModules().add(childModule);
      createModuleRepository.save(childModule);
      projectEntity.getModules().removeIf(entity -> entity.getId().equals(childModule.getId()));
      createModuleRepository.detachModuleFromProject(projectEntity.getId(), childModule.getId());
    }
    projectEntity.getModules().add(moduleEntity);
    getProjectRepository.save(projectEntity);
    createModuleRepository.save(moduleEntity);
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
    entity = createModuleRepository.findById(id).orElseThrow(() -> new ModuleNotFoundException(id));
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
            createModuleRepository
                .findById(m.getId())
                .orElseThrow(() -> new ModuleNotFoundException(m.getId())));
      }
    }
    return childModules;
  }
}

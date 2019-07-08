package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;

import java.util.ArrayList;
import java.util.List;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathDoesNotExistsException;
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

  @Autowired
  public CreateModuleAdapter(
          CreateModuleRepository createModuleRepository, GetProjectRepository getProjectRepository, ListModulesOfProjectRepository listModulesOfProjectRepository, TaskExecutor taskExecutor) {
    this.createModuleRepository = createModuleRepository;
    this.getProjectRepository = getProjectRepository;
    this.listModulesOfProjectRepository = listModulesOfProjectRepository;
    this.taskExecutor = taskExecutor;
  }

  /**
   * Adds a new module to the project.
   *
   * @param module The module to add.
   * @param projectId The project id/
   * @return The id of the newly created module.
   * @throws ProjectNotFoundException Thrown if a project with projectId is not found.
   */
  @Override
  public Long createModule(Module module, Long projectId) throws ProjectNotFoundException, ModuleAlreadyExistsException, ModulePathDoesNotExistsException {
    ModuleEntity moduleEntity = moduleMapper.mapDomainObject(module);
    ProjectEntity projectEntity =
        getProjectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));

    checkPathIsValid(moduleEntity, projectEntity);
    Long moduleId = createModuleRepository.save(moduleEntity).getId();

    taskExecutor.execute(() -> {
      if (!processExistingModules(projectEntity, moduleEntity)) {
        attachModuleToProject(projectEntity, moduleEntity);
      }
    });

    return moduleId;
  }

  /**
   * Checks for errors in the path and adds/removes backslashes where necessary
   *
   * @param moduleEntity The entity to perform checks on.
   * @param projectEntity The project to check the paths of
   */
  private void checkPathIsValid(ModuleEntity moduleEntity, ProjectEntity projectEntity) throws ModuleAlreadyExistsException,
          ModulePathDoesNotExistsException {

    //Check for a slash at the start and end of the path.
    if (!moduleEntity.getPath().endsWith("/")) {
      moduleEntity.setPath(moduleEntity.getPath().concat("/"));
    }
    if (moduleEntity.getPath().startsWith("/")) {
      moduleEntity.setPath(moduleEntity.getPath().substring(1));
    }

    //Check if a module with the same path already exists.
    for(ModuleEntity entity : listModulesOfProjectRepository.findModulesInProject(projectEntity.getId())){
      if(entity.getPath().equals(moduleEntity.getPath())){
        throw new ModuleAlreadyExistsException(moduleEntity.getPath());
      }
    }
  }

  /**
   * Attaches a module directly to a project.
   *
   * @param projectEntity The project to attach the module to.
   * @param moduleEntity The module to attach.
   * @return The id of the new module.
   */
  private boolean attachModuleToProject(ProjectEntity projectEntity, ModuleEntity moduleEntity) {

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
    List<ModuleEntity> childModules =
        findChildModules(projectEntity.getModules(), moduleEntity.getPath());
    if (!childModules.isEmpty()) {
      projectEntity.getModules().add(moduleEntity);
      for (ModuleEntity childModule : childModules) {
        childModule.setParentModule(moduleEntity);
        childModule.setProject(null);
        moduleEntity.getChildModules().add(childModule);
        createModuleRepository.save(childModule);
        projectEntity.getModules().remove(childModule);
        getProjectRepository.save(projectEntity);
      }
      createModuleRepository.save(moduleEntity);
      for (ModuleEntity childModule : childModules) {
        createModuleRepository.detachModuleFromProject(projectEntity.getId(), childModule.getId());
      }
    } else {
      projectEntity.getModules().add(moduleEntity);
      getProjectRepository.save(projectEntity);
      createModuleRepository.save(moduleEntity);
    }
    return true;
  }

  /**
   * Saves the new module as a child of an existing module.
   *
   * @param projectEntity The project this module is part of.
   * @param moduleEntity The new module entity.
   * @return The id of the new module entity.
   */
  private boolean processExistingModules(ProjectEntity projectEntity, ModuleEntity moduleEntity) {
    // Move all files contained in the module path from the parent to the new module.
    for (ModuleEntity m : projectEntity.getModules()) {
      ModuleEntity foundModule = findModule(m, moduleEntity.getPath());
      if (foundModule != null) {
        for (FileEntity fileEntity : foundModule.getFiles()) {
          if (fileEntity.getPath().startsWith(moduleEntity.getPath())) {
            moduleEntity.getFiles().add(fileEntity);
            createModuleRepository.detachFileFromModule(foundModule.getId(), fileEntity.getId());
          }
        }
        foundModule.getFiles().removeAll(moduleEntity.getFiles());

        // Check to see if the module could a have child
        List<ModuleEntity> childModules =
            findChildModules(foundModule.getChildModules(), moduleEntity.getPath());
        if (!childModules.isEmpty()) {
          foundModule.getChildModules().add(moduleEntity);
          for (ModuleEntity childModule : childModules) {
            childModule.setParentModule(moduleEntity);
            moduleEntity.getChildModules().add(childModule);
            foundModule.getChildModules().remove(childModule);
            createModuleRepository.save(foundModule);
            createModuleRepository.save(childModule);
          }
          createModuleRepository.save(moduleEntity);
          for (ModuleEntity childModule : childModules) {
            createModuleRepository.detachModuleFromModule(foundModule.getId(), childModule.getId());
          }
        } else {
          foundModule.getChildModules().add(moduleEntity);
          createModuleRepository.save(foundModule);
          createModuleRepository.save(moduleEntity);
        }
        return true;
      }
    }
    return false;
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

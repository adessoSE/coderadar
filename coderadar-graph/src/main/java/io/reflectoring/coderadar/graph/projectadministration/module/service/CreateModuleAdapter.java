package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import java.util.ArrayList;
import java.util.List;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateModuleAdapter implements CreateModulePort {
  private final ModuleRepository moduleRepository;
  private final ProjectRepository projectRepository;
  private final Session session;

  @Autowired
  public CreateModuleAdapter(
      ModuleRepository moduleRepository, ProjectRepository projectRepository, Session session) {
    this.moduleRepository = moduleRepository;
    this.projectRepository = projectRepository;
    this.session = session;
  }

  /**
   * Adds a new module to the project.
   *
   * @param moduleId The module id.
   * @param projectId The project id/
   * @throws ProjectNotFoundException Thrown if a project with projectId is not found.
   */
  @Override
  public void createModule(Long moduleId, Long projectId) {
    ModuleEntity moduleEntity =
        moduleRepository
            .findModuleById(moduleId)
            .orElseThrow(() -> new ModuleNotFoundException(moduleId));
    ProjectEntity projectEntity =
        projectRepository
            .findProjectById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    ModuleEntity foundModule = findParentModuleInProject(projectEntity, moduleEntity.getPath());
    if (foundModule != null) {
      attachModuleToModule(foundModule, moduleEntity);
    } else {
      attachModuleToProject(projectEntity, moduleEntity);
    }
    session.clear();
  }

  /**
   * Attaches a child module to a parent module and moves all files with a matching path to the
   * child module.
   *
   * @param parentModule The parent module
   * @param childModule The child module
   */
  private void attachModuleToModule(ModuleEntity parentModule, ModuleEntity childModule) {

    List<Long> childModuleFileIds = new ArrayList<>();
    for (FileEntity fileEntity : parentModule.getFiles()) {
      if (fileEntity.getPath().startsWith(childModule.getPath())) {
        childModule.getFiles().add(fileEntity);
        childModuleFileIds.add(fileEntity.getId());
      }
    }
    moduleRepository.detachFilesFromModule(parentModule.getId(), childModuleFileIds);
    parentModule.getFiles().removeAll(childModule.getFiles());

    // Check to see if the module could a have child
    for (ModuleEntity child :
        findChildModules(parentModule.getChildModules(), childModule.getPath())) {
      child.setParentModule(childModule);
      childModule.getChildModules().add(child);
      parentModule.getChildModules().removeIf(entity -> entity.getId().equals(child.getId()));
      moduleRepository.save(child);
      moduleRepository.detachModuleFromModule(parentModule.getId(), child.getId());
    }
    parentModule.getChildModules().add(childModule);
    childModule.setParentModule(parentModule);
    moduleRepository.save(parentModule);
    moduleRepository.save(childModule);
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
    List<Long> moduleFileIds = new ArrayList<>();
    for (FileEntity fileEntity : projectEntity.getFiles()) {
      if (fileEntity.getPath().startsWith(moduleEntity.getPath())) {
        moduleEntity.getFiles().add(fileEntity);
        moduleFileIds.add(fileEntity.getId());
      }
    }
    moduleRepository.detachFilesFromProject(projectEntity.getId(), moduleFileIds);
    projectEntity.getFiles().removeAll(moduleEntity.getFiles());

    // Check to see if the module could a have child
    for (ModuleEntity childModule :
        findChildModules(projectEntity.getModules(), moduleEntity.getPath())) {
      childModule.setParentModule(moduleEntity);
      childModule.setProject(null);
      moduleEntity.getChildModules().add(childModule);
      moduleRepository.save(childModule);
      projectEntity.getModules().removeIf(entity -> entity.getId().equals(childModule.getId()));
      moduleRepository.detachModuleFromProject(projectEntity.getId(), childModule.getId());
    }
    projectEntity.getModules().add(moduleEntity);
    projectRepository.save(projectEntity);
    moduleRepository.save(moduleEntity);
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
    entity = moduleRepository.findModuleById(id).orElseThrow(() -> new ModuleNotFoundException(id));
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
}

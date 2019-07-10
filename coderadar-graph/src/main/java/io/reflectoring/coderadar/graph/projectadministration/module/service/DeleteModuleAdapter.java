package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.DeleteModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class DeleteModuleAdapter implements DeleteModulePort {
  private final DeleteModuleRepository deleteModuleRepository;
  private final CreateProjectRepository createProjectRepository;
  private final GetProjectRepository getProjectRepository;
  private final TaskExecutor taskExecutor;

  @Autowired
  public DeleteModuleAdapter(
      DeleteModuleRepository deleteModuleRepository,
      CreateProjectRepository createProjectRepository,
      GetProjectRepository getProjectRepository,
      TaskExecutor taskExecutor) {
    this.deleteModuleRepository = deleteModuleRepository;
    this.createProjectRepository = createProjectRepository;
    this.getProjectRepository = getProjectRepository;
    this.taskExecutor = taskExecutor;
  }

  @Override
  public void delete(Long id, Long projectId)
      throws ModuleNotFoundException, ProjectIsBeingProcessedException {
    ProjectEntity projectEntity = getProject(projectId);
    if (projectEntity.isBeingProcessed()) {
      throw new ProjectIsBeingProcessedException(projectEntity.getId());
    }

    ModuleEntity moduleEntity =   deleteModuleRepository
            .findById(id)
            .orElseThrow(() -> new ModuleNotFoundException(id));

    projectEntity.setBeingProcessed(true);
    getProjectRepository.save(projectEntity);

    taskExecutor.execute(
        () -> {
          delete(moduleEntity);

          projectEntity.setBeingProcessed(false);
          getProjectRepository.save(projectEntity);
        });
  }

  @Override
  public void delete(Module module, Long projectId)
      throws ModuleNotFoundException, ProjectIsBeingProcessedException {
    ProjectEntity projectEntity = getProject(projectId);
    if (projectEntity.isBeingProcessed()) {
      throw new ProjectIsBeingProcessedException(projectEntity.getId());
    }

    ModuleEntity moduleEntity =   deleteModuleRepository
            .findById(module.getId())
            .orElseThrow(() -> new ModuleNotFoundException(module.getId()));

    projectEntity.setBeingProcessed(true);
    getProjectRepository.save(projectEntity);

    taskExecutor.execute(
        () -> {
          delete(moduleEntity);
          projectEntity.setBeingProcessed(false);
          getProjectRepository.save(projectEntity);
        });
  }

  private ProjectEntity getProject(Long projectId) {
    return getProjectRepository
        .findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException(projectId));
  }

  /**
   * Delete a ModuleEntity and adjusts all the relationships it had between the project, other
   * modules and files.
   *
   * @param moduleEntity The entity to delete
   */
  private void delete(ModuleEntity moduleEntity) {
    // If the module is a child of a project,
    // add all of its files and child modules to the parent project
    // and delete it
    if (moduleEntity.getParentModule() == null) {
      for (ModuleEntity child : moduleEntity.getChildModules()) {
        moduleEntity.getProject().getModules().add(child);
        deleteModuleRepository.detachModuleFromModule(moduleEntity.getId(), child.getId());
      }
      for (FileEntity fileEntity : moduleEntity.getFiles()) {
        moduleEntity.getProject().getFiles().add(fileEntity);
      }
      createProjectRepository.save(moduleEntity.getProject());
      deleteModuleRepository.detachModuleFromProject(
          moduleEntity.getProject().getId(), moduleEntity.getId());
    } else {

      // If the module is a child of another module,
      // add all of its files and child modules to the parent module
      // and delete it
      for (ModuleEntity child : moduleEntity.getChildModules()) {
        moduleEntity.getParentModule().getChildModules().add(child);
        deleteModuleRepository.detachModuleFromModule(moduleEntity.getId(), child.getId());
      }
      for (FileEntity fileEntity : moduleEntity.getFiles()) {
        moduleEntity.getParentModule().getFiles().add(fileEntity);
      }
      deleteModuleRepository.save(moduleEntity.getParentModule());
      deleteModuleRepository.detachModuleFromModule(
          moduleEntity.getParentModule().getId(), moduleEntity.getId());
    }
    deleteModuleRepository.deleteById(moduleEntity.getId());
  }
}

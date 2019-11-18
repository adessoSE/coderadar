package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import org.springframework.stereotype.Service;

@Service
public class DeleteModuleAdapter implements DeleteModulePort {
  private final ModuleRepository moduleRepository;
  private final ProjectRepository projectRepository;

  public DeleteModuleAdapter(
      ModuleRepository moduleRepository, ProjectRepository projectRepository) {
    this.moduleRepository = moduleRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public void delete(Long id, Long projectId) {
    ModuleEntity moduleEntity =
        moduleRepository.findById(id).orElseThrow(() -> new ModuleNotFoundException(id));

    delete(moduleEntity);
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
        moduleRepository.detachModuleFromModule(moduleEntity.getId(), child.getId());
      }
      for (FileEntity fileEntity : moduleEntity.getFiles()) {
        moduleEntity.getProject().getFiles().add(fileEntity);
      }
      projectRepository.save(moduleEntity.getProject());
      moduleRepository.detachModuleFromProject(
          moduleEntity.getProject().getId(), moduleEntity.getId());
    } else {

      // If the module is a child of another module,
      // add all of its files and child modules to the parent module
      // and delete it
      for (ModuleEntity child : moduleEntity.getChildModules()) {
        moduleEntity.getParentModule().getChildModules().add(child);
        moduleRepository.detachModuleFromModule(moduleEntity.getId(), child.getId());
      }
      for (FileEntity fileEntity : moduleEntity.getFiles()) {
        moduleEntity.getParentModule().getFiles().add(fileEntity);
      }
      moduleRepository.save(moduleEntity.getParentModule());
      moduleRepository.detachModuleFromModule(
          moduleEntity.getParentModule().getId(), moduleEntity.getId());
    }
    moduleRepository.deleteById(moduleEntity.getId());
  }
}

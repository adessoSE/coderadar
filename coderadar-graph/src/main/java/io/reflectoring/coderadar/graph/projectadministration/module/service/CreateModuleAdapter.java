package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateModuleAdapter implements CreateModulePort {
  private final CreateModuleRepository createModuleRepository;
  private final GetProjectRepository getProjectRepository;

  private final ModuleMapper moduleMapper = new ModuleMapper();

  @Autowired
  public CreateModuleAdapter(
      CreateModuleRepository createModuleRepository, GetProjectRepository getProjectRepository) {
    this.createModuleRepository = createModuleRepository;
    this.getProjectRepository = getProjectRepository;
  }

  @Override
  public Long createModule(Module module, Long projectId) throws ProjectNotFoundException {
    ModuleEntity moduleEntity = moduleMapper.mapDomainObject(module);
    ProjectEntity projectEntity =
        getProjectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));

    if (!moduleEntity.getPath().endsWith("/")) {
      moduleEntity.setPath(moduleEntity.getPath().concat("/"));
    }

    Long id = processExistingModules(projectEntity, moduleEntity);
    if (id != null) {
      return id;
    } else {
      moduleEntity.setProject(projectEntity);
      for (FileEntity fileEntity : projectEntity.getFiles()) {
        if (fileEntity.getPath().startsWith(moduleEntity.getPath())) {
          moduleEntity.getFiles().add(fileEntity);
          createModuleRepository.detachFileFromProject(projectEntity.getId(), fileEntity.getId());
        }
      }
      projectEntity.getFiles().removeAll(moduleEntity.getFiles());

      long moduleEntityId;
      ModuleEntity childModule =
          findModuleSomething(projectEntity.getModules(), moduleEntity.getPath());
      if (childModule != null) {
        childModule.setParentModule(moduleEntity);
        childModule.setProject(null);
        moduleEntity.getChildModules().add(childModule);
        createModuleRepository.save(childModule);
        projectEntity.getModules().add(moduleEntity);
        projectEntity.getModules().remove(childModule);
        getProjectRepository.save(projectEntity);
        moduleEntityId = createModuleRepository.save(moduleEntity).getId();
        createModuleRepository.detachModuleFromProject(projectEntity.getId(), childModule.getId());
      } else {
        projectEntity.getModules().add(moduleEntity);
        getProjectRepository.save(projectEntity);
        moduleEntityId = createModuleRepository.save(moduleEntity).getId();
      }
      return moduleEntityId;
    }
  }

  private ModuleEntity findModuleSomething(List<ModuleEntity> modules, String path) {
    for (ModuleEntity m : modules) {
      if (m.getPath().startsWith(path)) {
        return createModuleRepository
            .findById(m.getId())
            .orElseThrow(() -> new ModuleNotFoundException(m.getId()));
      }
    }
    return null;
  }

  private Long processExistingModules(ProjectEntity projectEntity, ModuleEntity moduleEntity) {
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

        long moduleEntityId;
        ModuleEntity childModule =
            findModuleSomething(foundModule.getChildModules(), moduleEntity.getPath());
        if (childModule != null) {
          childModule.setParentModule(moduleEntity);
          moduleEntity.getChildModules().add(childModule);
          createModuleRepository.save(childModule);
          foundModule.getChildModules().add(moduleEntity);
          foundModule.getChildModules().remove(childModule);
          createModuleRepository.save(foundModule);
          moduleEntityId = createModuleRepository.save(moduleEntity).getId();
          createModuleRepository.detachModuleFromModule(foundModule.getId(), childModule.getId());
        } else {
          foundModule.getChildModules().add(moduleEntity);
          createModuleRepository.save(foundModule);
          moduleEntityId = createModuleRepository.save(moduleEntity).getId();
        }
        return moduleEntityId;
      }
    }
    return null;
  }

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
}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    projectEntity.getModules().sort(Comparator.comparingLong(o -> o.getPath().length()));
    Collections.reverse(projectEntity.getModules()); //TODO: CHECK SORTING
    // TODO: CHECK REVERSE DIRECTION
    for(ModuleEntity m : projectEntity.getModules()){
      if(moduleEntity.getPath().startsWith(m.getPath())){
        ModuleEntity moduleWithFiles = createModuleRepository.findById(m.getId()).orElseThrow(()-> new ModuleNotFoundException(m.getId()));
        for(FileEntity fileEntity : moduleWithFiles.getFiles()){
          if(fileEntity.getPath().startsWith(moduleEntity.getPath())) {
            moduleEntity.getFiles().add(fileEntity);
            createModuleRepository.detachFileFromModule(moduleWithFiles.getId(), fileEntity.getId());
          }
        }
        moduleWithFiles.getFiles().removeAll(moduleEntity.getFiles());
        moduleWithFiles.getChildModules().add(moduleEntity);
        moduleEntity.setParentModule(moduleWithFiles);
        createModuleRepository.save(moduleWithFiles);
        return createModuleRepository.save(moduleEntity).getId();
      }
    }

    moduleEntity.setProject(projectEntity);
    for(FileEntity fileEntity : projectEntity.getFiles()){
      if(fileEntity.getPath().startsWith(moduleEntity.getPath())) {
        moduleEntity.getFiles().add(fileEntity);
        createModuleRepository.detachFileFromProject(projectEntity.getId(), fileEntity.getId());
      }
    }
    projectEntity.getFiles().removeAll(moduleEntity.getFiles());
    projectEntity.getModules().add(moduleEntity);
    getProjectRepository.save(projectEntity);

    return createModuleRepository.save(moduleEntity).getId();
  }
}

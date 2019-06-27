package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
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
    moduleEntity.setProject(projectEntity);
    projectEntity.getModules().add(moduleEntity);
    // TODO: SAVE FILES
    getProjectRepository.save(projectEntity);
    return createModuleRepository.save(moduleEntity).getId();
  }
}

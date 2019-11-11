package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ListModulesOfProjectAdapter implements ListModulesOfProjectPort {
  private final ProjectRepository projectRepository;
  private final ModuleRepository moduleRepository;
  private final ModuleMapper moduleMapper = new ModuleMapper();

  public ListModulesOfProjectAdapter(
      ProjectRepository projectRepository, ModuleRepository moduleRepository) {
    this.projectRepository = projectRepository;
    this.moduleRepository = moduleRepository;
  }

  @Override
  public Collection<Module> listModules(Long projectId) {
    Optional<ProjectEntity> project = projectRepository.findProjectById(projectId);
    if (project.isPresent()) {
      return moduleMapper.mapNodeEntities(moduleRepository.findModulesInProject(projectId));
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }

  @Override
  public List<GetModuleResponse> listModuleResponses(Long projectId) {
    Optional<ProjectEntity> project = projectRepository.findProjectById(projectId);
    if (project.isPresent()) {
      List<GetModuleResponse> getModuleResponses = new ArrayList<>();
      for (ModuleEntity m : moduleRepository.findModulesInProject(projectId)) {
        GetModuleResponse response = new GetModuleResponse();
        response.setId(m.getId());
        response.setPath(m.getPath());
        getModuleResponses.add(response);
      }
      return getModuleResponses;
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}

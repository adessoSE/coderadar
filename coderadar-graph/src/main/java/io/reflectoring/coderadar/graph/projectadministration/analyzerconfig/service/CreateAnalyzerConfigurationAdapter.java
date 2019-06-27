package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAnalyzerConfigurationAdapter implements CreateAnalyzerConfigurationPort {
  private final CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();
  private final GetProjectRepository getProjectRepository;

  @Autowired
  public CreateAnalyzerConfigurationAdapter(
      CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository,
      GetProjectRepository getProjectRepository) {
    this.createAnalyzerConfigurationRepository = createAnalyzerConfigurationRepository;
    this.getProjectRepository = getProjectRepository;
  }

  @Override
  public Long create(AnalyzerConfiguration entity, Long projectId) throws ProjectNotFoundException {
    AnalyzerConfigurationEntity analyzerConfigurationEntity =
        analyzerConfigurationMapper.mapDomainObject(entity);
    ProjectEntity projectEntity =
        getProjectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    analyzerConfigurationEntity.setProject(projectEntity);
    projectEntity.getAnalyzerConfigurations().add(analyzerConfigurationEntity);
    getProjectRepository.save(projectEntity);
    return createAnalyzerConfigurationRepository.save(analyzerConfigurationEntity).getId();
  }
}

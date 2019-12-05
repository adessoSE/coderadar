package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import org.springframework.stereotype.Service;

@Service
public class CreateAnalyzerConfigurationAdapter implements CreateAnalyzerConfigurationPort {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();
  private final ProjectRepository projectRepository;

  public CreateAnalyzerConfigurationAdapter(
      AnalyzerConfigurationRepository analyzerConfigurationRepository,
      ProjectRepository projectRepository) {
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public Long create(AnalyzerConfiguration entity, Long projectId) {
    AnalyzerConfigurationEntity analyzerConfigurationEntity =
        analyzerConfigurationMapper.mapDomainObject(entity);
    ProjectEntity projectEntity =
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    analyzerConfigurationEntity.setProject(projectEntity);
    projectEntity.getAnalyzerConfigurations().add(analyzerConfigurationEntity);
    projectRepository.save(projectEntity);
    return analyzerConfigurationRepository.save(analyzerConfigurationEntity).getId();
  }
}

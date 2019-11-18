package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.ListAnalyzerConfigurationsPort;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class ListAnalyzerConfigurationsAdapter implements ListAnalyzerConfigurationsPort {
  private final ProjectRepository projectRepository;
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  public ListAnalyzerConfigurationsAdapter(
      ProjectRepository projectRepository,
      AnalyzerConfigurationRepository analyzerConfigurationRepository) {
    this.projectRepository = projectRepository;
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
  }

  @Override
  public Collection<AnalyzerConfiguration> get(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    return analyzerConfigurationMapper.mapNodeEntities(
        analyzerConfigurationRepository.findByProjectId(projectId));
  }
}

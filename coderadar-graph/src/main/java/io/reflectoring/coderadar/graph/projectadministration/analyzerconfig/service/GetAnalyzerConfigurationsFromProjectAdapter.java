package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationsFromProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAnalyzerConfigurationsFromProjectAdapter
    implements GetAnalyzerConfigurationsFromProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final GetAnalyzerConfigurationsFromProjectRepository
      getAnalyzerConfigurationsFromProjectRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  @Autowired
  public GetAnalyzerConfigurationsFromProjectAdapter(
      GetProjectRepository getProjectRepository,
      GetAnalyzerConfigurationsFromProjectRepository
          getAnalyzerConfigurationsFromProjectRepository) {

    this.getProjectRepository = getProjectRepository;
    this.getAnalyzerConfigurationsFromProjectRepository =
        getAnalyzerConfigurationsFromProjectRepository;
  }

  @Override
  public Collection<AnalyzerConfiguration> get(Long projectId) throws ProjectNotFoundException {
    getProjectRepository
        .findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException(projectId));
    return analyzerConfigurationMapper.mapNodeEntities(
        getAnalyzerConfigurationsFromProjectRepository.findByProjectId(projectId));
  }
}

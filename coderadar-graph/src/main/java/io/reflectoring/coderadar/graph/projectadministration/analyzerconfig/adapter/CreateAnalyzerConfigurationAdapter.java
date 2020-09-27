package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateAnalyzerConfigurationAdapter implements CreateAnalyzerConfigurationPort {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;

  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  @Override
  public Long create(AnalyzerConfiguration entity, long projectId) {
    AnalyzerConfigurationEntity analyzerConfigurationEntity =
        analyzerConfigurationMapper.mapDomainObject(entity);
    analyzerConfigurationRepository.save(analyzerConfigurationEntity, 0);
    analyzerConfigurationRepository.addConfigurationToProject(
        projectId, analyzerConfigurationEntity.getId());
    return analyzerConfigurationEntity.getId();
  }
}

package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;

public class AnalyzerConfigurationMapper
    implements Mapper<AnalyzerConfiguration, AnalyzerConfigurationEntity> {

  @Override
  public AnalyzerConfiguration mapGraphObject(AnalyzerConfigurationEntity nodeEntity) {
    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setId(nodeEntity.getId());
    analyzerConfiguration.setAnalyzerName(nodeEntity.getAnalyzerName());
    analyzerConfiguration.setEnabled(nodeEntity.getEnabled());
    return analyzerConfiguration;
  }

  @Override
  public AnalyzerConfigurationEntity mapDomainObject(AnalyzerConfiguration domainObject) {
    AnalyzerConfigurationEntity analyzerConfiguration = new AnalyzerConfigurationEntity();
    analyzerConfiguration.setAnalyzerName(domainObject.getAnalyzerName());
    analyzerConfiguration.setEnabled(domainObject.isEnabled());
    return analyzerConfiguration;
  }
}

package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get;

public interface GetAnalyzerConfigurationUseCase {
  GetAnalyzerConfigurationResponse getSingleAnalyzerConfiguration(Long id);
}

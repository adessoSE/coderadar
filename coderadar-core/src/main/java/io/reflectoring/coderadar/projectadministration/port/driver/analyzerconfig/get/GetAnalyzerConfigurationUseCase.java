package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get;

public interface GetAnalyzerConfigurationUseCase {
  GetAnalyzerConfigurationResponse getSingleAnalyzerConfiguration(Long id);
}

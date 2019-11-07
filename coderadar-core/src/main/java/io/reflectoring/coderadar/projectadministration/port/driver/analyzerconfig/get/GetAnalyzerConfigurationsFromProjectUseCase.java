package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get;

import java.util.List;

public interface GetAnalyzerConfigurationsFromProjectUseCase {
  List<GetAnalyzerConfigurationResponse> get(Long projectId);
}

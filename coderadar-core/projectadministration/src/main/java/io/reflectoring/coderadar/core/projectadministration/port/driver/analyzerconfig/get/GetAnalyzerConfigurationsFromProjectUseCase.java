package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get;

import java.util.List;

public interface GetAnalyzerConfigurationsFromProjectUseCase {
  List<GetAnalyzerConfigurationResponse> get(Long projectId);
}

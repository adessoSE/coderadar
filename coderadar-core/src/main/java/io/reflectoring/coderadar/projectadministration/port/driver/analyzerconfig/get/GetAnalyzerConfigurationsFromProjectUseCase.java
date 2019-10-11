package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import java.util.List;

public interface GetAnalyzerConfigurationsFromProjectUseCase {
  List<GetAnalyzerConfigurationResponse> get(Long projectId) throws ProjectNotFoundException;
}

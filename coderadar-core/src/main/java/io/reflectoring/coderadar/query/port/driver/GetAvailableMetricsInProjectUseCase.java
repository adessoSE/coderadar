package io.reflectoring.coderadar.query.port.driver;

import java.util.List;

public interface GetAvailableMetricsInProjectUseCase {

  /**
   * @param projectId The project id.
   * @return All of the available metrics in the current project.
   */
  List<String> get(long projectId);
}

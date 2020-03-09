package io.reflectoring.coderadar.query.port.driven;

import java.util.List;

public interface GetAvailableMetricsInProjectPort {

  /**
   * @param projectId The project id.
   * @return All of the available metrics in the current project.
   */
  List<String> get(long projectId);
}

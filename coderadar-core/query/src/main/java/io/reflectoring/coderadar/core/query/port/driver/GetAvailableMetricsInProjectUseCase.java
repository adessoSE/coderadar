package io.reflectoring.coderadar.core.query.port.driver;

import java.util.List;

public interface GetAvailableMetricsInProjectUseCase {
  List<String> get(Long projectId);
}

package io.reflectoring.coderadar.core.query.port.driver;

import io.reflectoring.coderadar.core.analyzer.domain.Commit;
import io.reflectoring.coderadar.core.analyzer.domain.MetricValueDTO;
import java.util.List;

public interface GetMetricValuesOfCommitUseCase {
  List<MetricValueDTO> get(Commit commit);

  List<MetricValueDTO> get(String commitHash);
}

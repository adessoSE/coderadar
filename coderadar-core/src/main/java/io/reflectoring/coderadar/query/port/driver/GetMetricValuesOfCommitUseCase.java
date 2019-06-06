package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.MetricValueDTO;
import java.util.List;

public interface GetMetricValuesOfCommitUseCase {
  List<MetricValueDTO> get(Commit commit);

  List<MetricValueDTO> get(String commitHash);
}

package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;

import java.util.List;

public interface GetMetricValuesOfCommitUseCase {
  List<MetricValue> get(Commit commit);

  List<MetricValue> get(String commitHash);
}

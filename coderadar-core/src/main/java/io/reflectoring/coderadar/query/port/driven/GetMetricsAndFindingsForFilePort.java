package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.MetricWithFindings;
import java.util.List;

public interface GetMetricsAndFindingsForFilePort {

  List<MetricWithFindings> getMetricsAndFindingsForFile(
      long projectId, String commitHash, String filepath);
}

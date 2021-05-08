package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.domain.MetricWithFindings;
import java.util.List;

public interface GetMetricsAndFindingsForFilePort {

  /**
   * @param projectId The id of the project.
   * @param commitHash The hash of the commit the file is in (16 chars).
   * @param filepath The path of the file.
   * @return All of the metrics for the file along with their location in it.
   */
  List<MetricWithFindings> getMetricsAndFindingsForFile(
      long projectId, long commitHash, String filepath);
}

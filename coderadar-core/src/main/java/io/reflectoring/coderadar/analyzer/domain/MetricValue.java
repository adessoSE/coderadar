package io.reflectoring.coderadar.analyzer.domain;

import io.reflectoring.coderadar.plugin.api.Finding;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/** A single metric for a given File and Commit. */
@Data
@AllArgsConstructor
public class MetricValue {
  private final String name;
  private final long value;
  private final long commitId;
  private final long fileId;

  private final List<Finding> findings;
}

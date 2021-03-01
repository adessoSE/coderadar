package io.reflectoring.coderadar.analyzer.domain;

import io.reflectoring.coderadar.plugin.api.Finding;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/** A single metric for a given File and Commit. */
@Data
@AllArgsConstructor
public class MetricValue {
  private final int name;
  private final int value;
  private final long commitId;
  private final long fileId;

  private final List<Finding> findings;
}

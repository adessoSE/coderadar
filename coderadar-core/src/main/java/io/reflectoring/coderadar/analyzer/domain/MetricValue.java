package io.reflectoring.coderadar.analyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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

package io.reflectoring.coderadar.analyzer.domain;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/** A single metric for a given File and Commit. */
@Data
@RequiredArgsConstructor
public class MetricValue {
  private final String name;
  private final long value;
  private final long commitId;
  private final long fileId;

  private final List<Finding> findings;
}

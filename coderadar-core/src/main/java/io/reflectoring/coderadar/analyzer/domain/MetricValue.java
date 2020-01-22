package io.reflectoring.coderadar.analyzer.domain;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/** A single metric for a given File and Commit. */
@Data
@RequiredArgsConstructor
public class MetricValue {
  private Long id;
  private final String name;
  private final Long value;
  private final Commit commit;
  private final List<Finding> findings;
  private final Long fileId;
}

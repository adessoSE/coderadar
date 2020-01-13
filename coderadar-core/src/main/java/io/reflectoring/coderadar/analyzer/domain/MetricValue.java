package io.reflectoring.coderadar.analyzer.domain;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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

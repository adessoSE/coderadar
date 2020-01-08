package io.reflectoring.coderadar.projectadministration.domain;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

/** Represents a file in a VCS repository. */
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class File {
  private long id;
  private String path;

  @EqualsAndHashCode.Exclude private List<File> oldFiles = new ArrayList<>();
  @EqualsAndHashCode.Exclude private List<MetricValue> metricValues = new ArrayList<>();
}

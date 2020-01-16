package io.reflectoring.coderadar.projectadministration.domain;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** Represents a file in a VCS repository. */
@Data
@EqualsAndHashCode
public class File {
  private Long id;
  private String path;

  @EqualsAndHashCode.Exclude private List<File> oldFiles = new ArrayList<>();

  @EqualsAndHashCode.Exclude private List<MetricValue> metricValues = new ArrayList<>();
}

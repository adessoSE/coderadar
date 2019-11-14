package io.reflectoring.coderadar.projectadministration.domain;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/** Represents a file in a VCS repository. */
@Data
public class File {
  private Long id;
  private String path;

  private List<File> oldFiles = new ArrayList<>();

  private List<MetricValue> metricValues = new ArrayList<>();

  @ToString.Exclude private List<FileToCommitRelationship> commits = new ArrayList<>();
}

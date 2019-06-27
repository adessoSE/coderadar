package io.reflectoring.coderadar.analyzer.domain;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/** Represents a file in a VCS repository. */
@Data
public class File {
  private Long id;
  private String path;

  private List<MetricValue> metricValues = new LinkedList<>();

  private List<FileToCommitRelationship> commits = new LinkedList<>();
}

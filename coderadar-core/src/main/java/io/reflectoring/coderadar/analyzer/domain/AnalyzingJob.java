package io.reflectoring.coderadar.analyzer.domain;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import lombok.Data;

import java.util.Date;

@Data
public class AnalyzingJob {
  private Long id;
  private Date from; // TODO: Maybe use date converter.
  private boolean active;
  private boolean rescan;

  private Project project;
}

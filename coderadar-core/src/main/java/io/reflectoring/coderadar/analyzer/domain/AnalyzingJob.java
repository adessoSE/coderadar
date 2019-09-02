package io.reflectoring.coderadar.analyzer.domain;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Date;
import lombok.Data;

@Data
public class AnalyzingJob {
  private Long id;
  private Date from;
  private boolean active;
  private boolean rescan;

  private Project project;
}

package io.reflectoring.coderadar.analyzer.domain;

import java.util.Date;
import lombok.Data;

/** An object that represents a single analyzing task for a single project. */
@Data
public class AnalyzingJob {
  private Long id;
  private Date from;
  private boolean active;
  private boolean rescan;
}

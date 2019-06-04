package io.reflectoring.coderadar.core.analyzer.port.driver;

import java.util.Date;
import lombok.Value;

@Value
public class StartAnalyzingCommand {
  private Long projectId;
  private Date from;
  private Boolean rescan;
}

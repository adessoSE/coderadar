package io.reflectoring.coderadar.core.analyzer.port.driver;

import java.util.Date;
import lombok.Value;

@Value
public class StartAnalyzingCommand {
  Long projectId;
  Date from;
  Boolean rescan;
}

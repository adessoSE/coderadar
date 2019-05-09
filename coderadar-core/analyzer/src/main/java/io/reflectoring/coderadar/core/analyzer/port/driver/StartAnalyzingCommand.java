package io.reflectoring.coderadar.core.analyzer.port.driver;

import lombok.Value;

import java.util.Date;

@Value
public class StartAnalyzingCommand {
  Long projectId;
  Date from;
  Boolean rescan;
}

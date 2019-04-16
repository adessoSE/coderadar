package io.reflectoring.coderadar.core.query.port.driver;

import java.util.List;
import lombok.Value;

@Value
public class GetMetricsForAllFilesInCommitCommand {
  String commitHash;
  List<String> metricNames;
}

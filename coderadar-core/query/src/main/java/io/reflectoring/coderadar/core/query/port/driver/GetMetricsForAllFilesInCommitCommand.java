package io.reflectoring.coderadar.core.query.port.driver;

import lombok.Value;

import java.util.List;

@Value
public class GetMetricsForAllFilesInCommitCommand {
  String commitHash;
  List<String> metricNames;
}

package io.reflectoring.coderadar.analyzer.port.driven;

public interface GetAnalyzingStatusPort {
  boolean get(Long projectId);
}

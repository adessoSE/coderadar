package io.reflectoring.coderadar.core.analyzer.port.driven;

public interface GetAnalyzingStatusPort {
  boolean get(Long projectId);
}

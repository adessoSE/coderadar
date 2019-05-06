package io.reflectoring.coderadar.core.analyzer.port.driver;

public interface GetAnalyzingStatusUseCase {
  boolean get(Long projectId);
}

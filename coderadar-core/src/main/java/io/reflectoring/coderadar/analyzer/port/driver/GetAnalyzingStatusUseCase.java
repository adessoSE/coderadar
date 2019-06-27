package io.reflectoring.coderadar.analyzer.port.driver;

public interface GetAnalyzingStatusUseCase {
  boolean get(Long projectId);
}

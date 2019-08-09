package io.reflectoring.coderadar.analyzer.port.driver;

public interface GetAnalyzingStatusUseCase {
  Boolean get(Long projectId);
}

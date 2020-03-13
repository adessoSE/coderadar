package io.reflectoring.coderadar.analyzer.port.driver;

public interface GetAnalyzingStatusUseCase {

  /**
   * @param projectId The id of the project to check.
   * @return True if the project is being analyzed, false otherwise
   */
  boolean getStatus(long projectId);
}

package io.reflectoring.coderadar.analyzer.port.driven;

public interface GetAnalyzingStatusPort {

  /**
   * @param projectId The id of the project to check.
   * @return True if the project is being analyzed, false otherwise
   */
  boolean getStatus(Long projectId);
}

package io.reflectoring.coderadar.analyzer.port.driven;

public interface SetAnalyzingStatusPort {

  /**
   * Sets the analyzing status of the project with the given id.
   *
   * @param projectId The id of the project.
   * @param status The status to set.
   */
  void setStatus(Long projectId, boolean status);
}

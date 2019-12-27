package io.reflectoring.coderadar.analyzer.port.driven;

public interface StopAnalyzingPort {

  /**
   * Deletes the analyzing job for the given project.
   *
   * @param projectId The project id.
   */
  void stop(Long projectId);
}

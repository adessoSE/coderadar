package io.reflectoring.coderadar.analyzer.port.driven;

public interface ResetAnalysisPort {

  /**
   * Deletes all metric values and findings for the given project.
   *
   * @param projectId The id of the project.
   */
  void resetAnalysis(Long projectId);
}

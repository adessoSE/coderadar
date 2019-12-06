package io.reflectoring.coderadar.analyzer.port.driver;

public interface ResetAnalysisUseCase {

  /**
   * Deletes all metric values and findings for the given project.
   *
   * @param projectId The id of the project.
   */
  void resetAnalysis(Long projectId);
}

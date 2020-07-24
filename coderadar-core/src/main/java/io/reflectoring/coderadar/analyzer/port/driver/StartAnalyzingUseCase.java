package io.reflectoring.coderadar.analyzer.port.driver;

import java.util.List;

public interface StartAnalyzingUseCase {

  /**
   * Starts the analysis of a project for all branches.
   *
   * @param projectId The id of the project to analyze.
   */
  void start(long projectId);

  /**
   * Starts the analysis of a project for the given branches.
   *
   * @param projectId The id of the project to analyze.
   */
  void start(long projectId, List<String> branches);
}

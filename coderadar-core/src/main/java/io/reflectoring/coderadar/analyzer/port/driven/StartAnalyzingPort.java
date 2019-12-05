package io.reflectoring.coderadar.analyzer.port.driven;

import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;

public interface StartAnalyzingPort {

  /**
   * Creates an analyzing job for the given project.
   *
   * @param command Command containing analysis parameters.
   * @param projectId The id of the project to analyze.
   * @return Id of the analyzing job.
   */
  Long start(StartAnalyzingCommand command, Long projectId);
}

package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.analyzer.port.driver.ResetAnalysisUseCase;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetAnalysisService implements ResetAnalysisUseCase {
  private final ResetAnalysisPort resetAnalysisPort;
  private final GetProjectPort getProjectPort;
  private final ProcessProjectService processProjectService;

  private static final Logger logger = LoggerFactory.getLogger(ResetAnalysisService.class);

  /**
   * Deletes all metric values and findings for the given project.
   *
   * @param projectId The id of the project.
   */
  @Override
  public void resetAnalysis(long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    processProjectService.executeTask(
        () -> {
          resetAnalysisPort.resetAnalysis(projectId);
          logger.info("Reset analysis results for project with id {}", projectId);
        },
        projectId);
  }
}

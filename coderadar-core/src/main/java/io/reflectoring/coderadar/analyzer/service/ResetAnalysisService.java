package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.analyzer.port.driver.ResetAnalysisUseCase;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetAnalysisService implements ResetAnalysisUseCase {
  private final ResetAnalysisPort resetAnalysisPort;
  private final GetProjectPort getProjectPort;
  private final ProcessProjectService processProjectService;

  @Autowired
  public ResetAnalysisService(
      ResetAnalysisPort resetAnalysisPort,
      GetProjectPort getProjectPort,
      ProcessProjectService processProjectService) {
    this.resetAnalysisPort = resetAnalysisPort;
    this.getProjectPort = getProjectPort;
    this.processProjectService = processProjectService;
  }

  @Override
  public void resetAnalysis(Long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    processProjectService.executeTask(() -> resetAnalysisPort.resetAnalysis(projectId), projectId);
  }
}

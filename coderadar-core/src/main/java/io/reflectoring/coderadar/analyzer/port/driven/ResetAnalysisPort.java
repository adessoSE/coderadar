package io.reflectoring.coderadar.analyzer.port.driven;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;

public interface ResetAnalysisPort {
  void resetAnalysis(Long projectId) throws ProjectIsBeingProcessedException;
}

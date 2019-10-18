package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetAnalysisAdapter implements ResetAnalysisPort {
  private final CommitRepository commitRepository;
  private final ProjectRepository projectRepository;

  @Autowired
  public ResetAnalysisAdapter(
      CommitRepository commitRepository, ProjectRepository projectRepository) {
    this.commitRepository = commitRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public void resetAnalysis(Long projectId) throws ProjectIsBeingProcessedException {
    projectRepository.deleteProjectFindings(projectId);
    projectRepository.deleteProjectMetrics(projectId);
    commitRepository.resetAnalyzedStatus(projectId);
  }
}

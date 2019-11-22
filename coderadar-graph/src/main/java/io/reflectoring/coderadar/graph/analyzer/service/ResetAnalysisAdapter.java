package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.graph.analyzer.repository.AnalyzingJobRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ResetAnalysisAdapter implements ResetAnalysisPort {
  private final CommitRepository commitRepository;
  private final ProjectRepository projectRepository;
  private final AnalyzingJobRepository analyzingJobRepository;

  public ResetAnalysisAdapter(
      CommitRepository commitRepository,
      ProjectRepository projectRepository,
      AnalyzingJobRepository analyzingJobRepository) {
    this.commitRepository = commitRepository;
    this.projectRepository = projectRepository;
    this.analyzingJobRepository = analyzingJobRepository;
  }

  @Override
  public void resetAnalysis(Long projectId) {
    commitRepository.resetAnalyzedStatus(projectId);
    while (projectRepository.deleteProjectFindings(projectId) > 0) ;
    while (projectRepository.deleteProjectMetrics(projectId) > 0) ;
    analyzingJobRepository.deleteByProjectId(projectId);
  }
}

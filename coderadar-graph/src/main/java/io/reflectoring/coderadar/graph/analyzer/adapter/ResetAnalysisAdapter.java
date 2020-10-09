package io.reflectoring.coderadar.graph.analyzer.adapter;

import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetAnalysisAdapter implements ResetAnalysisPort {
  private final CommitRepository commitRepository;
  private final ProjectRepository projectRepository;

  @Override
  public void resetAnalysis(long projectId) {
    commitRepository.resetAnalyzedStatus(projectId);

    /*
     * The empty while loops are necessary because only 10000 entities can be deleted at a time.
     * @see ProjectRepository#deleteProjectMetrics(Long)
     */
    while (projectRepository.deleteProjectMetrics(projectId) > 0) ;
  }
}

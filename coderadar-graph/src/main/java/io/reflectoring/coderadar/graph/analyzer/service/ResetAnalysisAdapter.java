package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetAnalysisAdapter implements ResetAnalysisPort {
  private final CommitRepository commitRepository;
  private final MetricRepository metricRepository;

  @Autowired
  public ResetAnalysisAdapter(
      CommitRepository commitRepository, MetricRepository metricRepository) {
    this.commitRepository = commitRepository;
    this.metricRepository = metricRepository;
  }

  @Override
  public void resetAnalysis(Long projectId) {
    metricRepository.deleteAllMetricValuesAndFindingsFromProject(projectId);
    commitRepository.resetAnalyzedStatus(projectId);
  }
}

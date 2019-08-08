package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetAnalysisAdapter implements ResetAnalysisPort {
    private final CommitRepository commitRepository;
    private final MetricValueRepository metricValueRepository;

    @Autowired
    public ResetAnalysisAdapter(CommitRepository commitRepository, MetricValueRepository metricValueRepository) {
        this.commitRepository = commitRepository;
        this.metricValueRepository = metricValueRepository;
    }

    @Override
    public void resetAnalysis(Long projectId) {
        metricValueRepository.deleteAllMetricValuesFromProject(projectId);
        commitRepository.resetAnalyzedStatus(projectId);
    }
}

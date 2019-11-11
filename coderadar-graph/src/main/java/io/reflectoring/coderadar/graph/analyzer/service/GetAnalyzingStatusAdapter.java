package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.GetAnalyzingStatusPort;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.AnalyzingJobRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAnalyzingStatusAdapter implements GetAnalyzingStatusPort {
  private final AnalyzingJobRepository analyzingJobRepository;

  @Autowired
  public GetAnalyzingStatusAdapter(AnalyzingJobRepository analyzingJobRepository) {
    this.analyzingJobRepository = analyzingJobRepository;
  }

  @Override
  public boolean get(Long projectId) {
    AnalyzingJobEntity analyzingJob =
        analyzingJobRepository
            .findByProjectId(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    return analyzingJob.isActive();
  }
}

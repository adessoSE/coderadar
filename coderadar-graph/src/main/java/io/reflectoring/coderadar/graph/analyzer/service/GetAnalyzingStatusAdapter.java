package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.GetAnalyzingStatusPort;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAnalyzingStatusAdapter implements GetAnalyzingStatusPort {
  private final GetAnalyzingStatusRepository getAnalyzingStatusRepository;

  @Autowired
  public GetAnalyzingStatusAdapter(GetAnalyzingStatusRepository getAnalyzingStatusRepository) {
    this.getAnalyzingStatusRepository = getAnalyzingStatusRepository;
  }

  @Override
  public boolean get(Long projectId) throws ProjectNotFoundException {
    AnalyzingJobEntity analyzingJob =
        getAnalyzingStatusRepository
            .findByProjectId(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    return analyzingJob.isActive();
  }
}

package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.core.analyzer.port.driven.GetAnalyzingStatusPort;
import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzingJob;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("GetAnalyzingStatusServiceNeo4j")
public class GetAnalyzingStatusService implements GetAnalyzingStatusPort {
  private final GetAnalyzingStatusRepository getAnalyzingStatusRepository;

  @Autowired
  public GetAnalyzingStatusService(GetAnalyzingStatusRepository getAnalyzingStatusRepository) {
    this.getAnalyzingStatusRepository = getAnalyzingStatusRepository;
  }

  @Override
  public boolean get(Long projectId) {
    Optional<AnalyzingJob> analyzingJob = getAnalyzingStatusRepository.findByProject_Id(projectId);

    if (analyzingJob.isPresent()) {
      return analyzingJob.get().isActive();
    } else {
      throw new AnalyzingJobNotStartedException("Analyzing job hasn't been started yet.");
    }
  }
}

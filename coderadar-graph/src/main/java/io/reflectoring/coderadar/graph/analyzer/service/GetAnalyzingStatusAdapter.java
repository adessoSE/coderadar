package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.analyzer.domain.AnalyzingJob;
import io.reflectoring.coderadar.analyzer.port.driven.GetAnalyzingStatusPort;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import java.util.Optional;
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
  public boolean get(Long projectId) {
    Optional<AnalyzingJob> analyzingJob = getAnalyzingStatusRepository.findByProject_Id(projectId);

    if (analyzingJob.isPresent()) {
      return analyzingJob.get().isActive();
    } else {
      throw new AnalyzingJobNotStartedException("Analyzing job hasn't been started yet.");
    }
  }
}

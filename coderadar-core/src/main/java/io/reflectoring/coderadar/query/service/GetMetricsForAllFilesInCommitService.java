package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.port.driven.GetMetricsForAllFilesInCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForAllFilesInCommitUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetMetricsForAllFilesInCommitService implements GetMetricsForAllFilesInCommitUseCase {
  private final GetMetricsForAllFilesInCommitPort getMetricsForAllFilesInCommitPort;

//TODO: IMPLEMENT
  public GetMetricsForAllFilesInCommitService(GetMetricsForAllFilesInCommitPort getMetricsForAllFilesInCommitPort) {
    this.getMetricsForAllFilesInCommitPort = getMetricsForAllFilesInCommitPort;
  }
}

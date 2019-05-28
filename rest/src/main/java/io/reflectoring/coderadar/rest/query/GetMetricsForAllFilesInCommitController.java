package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.core.query.port.driver.GetMetricsForAllFilesInCommitUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMetricsForAllFilesInCommitController {
  private final GetMetricsForAllFilesInCommitUseCase getMetricsForAllFilesInCommitUseCase;

  @Autowired
  public GetMetricsForAllFilesInCommitController(
      GetMetricsForAllFilesInCommitUseCase getMetricsForAllFilesInCommitUseCase) {
    this.getMetricsForAllFilesInCommitUseCase = getMetricsForAllFilesInCommitUseCase;
  }
}

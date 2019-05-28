package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.core.query.port.driver.GetMetricValuesOfCommitUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMetricValuesOfCommitController {
  private final GetMetricValuesOfCommitUseCase getMetricValuesOfCommitUseCase;

  @Autowired
  public GetMetricValuesOfCommitController(
      GetMetricValuesOfCommitUseCase getMetricValuesOfCommitUseCase) {
    this.getMetricValuesOfCommitUseCase = getMetricValuesOfCommitUseCase;
  }
}

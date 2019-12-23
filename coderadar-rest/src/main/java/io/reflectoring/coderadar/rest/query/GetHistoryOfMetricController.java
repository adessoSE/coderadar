package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetHistoryOfMetricCommand;
import io.reflectoring.coderadar.query.port.driver.GetHistoryOfMetricUseCase;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetHistoryOfMetricController {
  private final GetHistoryOfMetricUseCase getHistoryOfMetricUseCase;

  public GetHistoryOfMetricController(GetHistoryOfMetricUseCase getHistoryOfMetricUseCase) {
    this.getHistoryOfMetricUseCase = getHistoryOfMetricUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/metricvalues/history", consumes = "application/json", produces = "application/json")
  public ResponseEntity getHistoryOfMetric(@RequestBody @Validated GetHistoryOfMetricCommand command, @PathVariable Long projectId) {
    return new ResponseEntity<>(new ErrorMessageResponse("This functionality is not implemented yet."), HttpStatus.NOT_IMPLEMENTED);
  }
}

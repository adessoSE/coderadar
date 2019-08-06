package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetMetricValuesOfCommitUseCase;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class GetMetricValuesOfCommitController {
  private final GetMetricValuesOfCommitUseCase getMetricValuesOfCommitUseCase;

  @Autowired
  public GetMetricValuesOfCommitController(
      GetMetricValuesOfCommitUseCase getMetricValuesOfCommitUseCase) {
    this.getMetricValuesOfCommitUseCase = getMetricValuesOfCommitUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/metricvalues/perCommit")
  public ResponseEntity getMetricValues(@Validated @RequestBody GetMetricsForCommitCommand command, @PathVariable("projectId") Long projectId){
    return new ResponseEntity<>(getMetricValuesOfCommitUseCase.get(command, projectId), HttpStatus.OK);
  }
}

package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetMetricsForAllFilesInCommitUseCase;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GetMetricsForAllFilesInCommitController {
  private final GetMetricsForAllFilesInCommitUseCase getMetricsForAllFilesInCommitUseCase;

  @Autowired
  public GetMetricsForAllFilesInCommitController(
      GetMetricsForAllFilesInCommitUseCase getMetricsForAllFilesInCommitUseCase) {
    this.getMetricsForAllFilesInCommitUseCase = getMetricsForAllFilesInCommitUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/metricvalues/tree")
  public ResponseEntity getMetricValues(@Validated @RequestBody GetMetricsForCommitCommand command, @PathVariable("projectId") Long projectId){
    return new ResponseEntity<>(getMetricsForAllFilesInCommitUseCase.get(command, projectId), HttpStatus.OK);
  }
}

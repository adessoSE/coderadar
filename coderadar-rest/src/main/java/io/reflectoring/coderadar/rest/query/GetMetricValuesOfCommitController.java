package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driver.commitmetrics.GetMetricValuesOfCommitCommand;
import io.reflectoring.coderadar.query.port.driver.commitmetrics.GetMetricValuesOfCommitUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetMetricValuesOfCommitController implements AbstractBaseController {
  private final GetMetricValuesOfCommitUseCase getMetricValuesOfCommitUseCase;
  private final AuthenticationService authenticationService;

  @RequestMapping(
      method = {RequestMethod.POST, RequestMethod.GET},
      path = "/projects/{projectId}/metricvalues/perCommit",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<MetricValueForCommit>> getMetricValues(
      @Validated @RequestBody GetMetricValuesOfCommitCommand command,
      @PathVariable("projectId") long projectId) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(
        getMetricValuesOfCommitUseCase.get(projectId, command), HttpStatus.OK);
  }
}

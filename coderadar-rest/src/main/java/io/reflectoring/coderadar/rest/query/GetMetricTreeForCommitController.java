package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.domain.MetricTree;
import io.reflectoring.coderadar.query.port.driver.metrictree.GetMetricTreeForCommitCommand;
import io.reflectoring.coderadar.query.port.driver.metrictree.GetMetricTreeForCommitUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMetricTreeForCommitController implements AbstractBaseController {
  private final GetMetricTreeForCommitUseCase getMetricTreeForCommitUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(
      path = "/projects/{projectId}/metricvalues/tree",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MetricTree> getMetricValues(
      @Validated @RequestBody GetMetricTreeForCommitCommand command,
      @PathVariable("projectId") long projectId) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(
        getMetricTreeForCommitUseCase.get(projectId, command), HttpStatus.OK);
  }
}

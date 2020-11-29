package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetAvailableMetricsInProjectUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetAvailableMetricsInProjectController implements AbstractBaseController {
  private final GetAvailableMetricsInProjectUseCase getAvailableMetricsInProjectUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(path = "/projects/{projectId}/metrics", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<String>> getMetrics(@PathVariable("projectId") long projectId) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(getAvailableMetricsInProjectUseCase.get(projectId), HttpStatus.OK);
  }
}

package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetAvailableMetricsInProjectUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Transactional
@RestController
public class GetAvailableMetricsInProjectController implements AbstractBaseController {
  private final GetAvailableMetricsInProjectUseCase getAvailableMetricsInProjectUseCase;

  public GetAvailableMetricsInProjectController(
      GetAvailableMetricsInProjectUseCase getAvailableMetricsInProjectUseCase) {
    this.getAvailableMetricsInProjectUseCase = getAvailableMetricsInProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/metrics", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<String>> getMetrics(@PathVariable("projectId") long projectId) {
    return new ResponseEntity<>(getAvailableMetricsInProjectUseCase.get(projectId), HttpStatus.OK);
  }
}

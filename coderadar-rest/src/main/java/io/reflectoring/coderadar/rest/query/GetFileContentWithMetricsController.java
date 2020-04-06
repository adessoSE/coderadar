package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.domain.FileContentWithMetrics;
import io.reflectoring.coderadar.query.port.driver.GetFileContentWithMetricsCommand;
import io.reflectoring.coderadar.query.port.driver.GetFileContentWithMetricsUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class GetFileContentWithMetricsController {

  private final GetFileContentWithMetricsUseCase useCase;

  public GetFileContentWithMetricsController(GetFileContentWithMetricsUseCase useCase) {
    this.useCase = useCase;
  }

  @RequestMapping(
      method = {RequestMethod.GET, RequestMethod.POST},
      path = "/projects/{projectId}/files/content",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FileContentWithMetrics> getFileContentWithMetrics(
      @PathVariable Long projectId,
      @RequestBody @Validated GetFileContentWithMetricsCommand command) {
    return new ResponseEntity<>(
        useCase.getFileContentWithMetrics(projectId, command), HttpStatus.OK);
  }
}

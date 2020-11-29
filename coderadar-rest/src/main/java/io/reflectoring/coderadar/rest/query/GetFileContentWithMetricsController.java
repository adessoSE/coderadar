package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.domain.FileContentWithMetrics;
import io.reflectoring.coderadar.query.port.driver.filecontent.GetFileContentWithMetricsCommand;
import io.reflectoring.coderadar.query.port.driver.filecontent.GetFileContentWithMetricsUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetFileContentWithMetricsController implements AbstractBaseController {
  private final GetFileContentWithMetricsUseCase useCase;
  private final AuthenticationService authenticationService;

  @RequestMapping(
      method = {RequestMethod.GET, RequestMethod.POST},
      path = "/projects/{projectId}/files/content",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FileContentWithMetrics> getFileContentWithMetrics(
      @PathVariable Long projectId,
      @RequestBody @Validated GetFileContentWithMetricsCommand command) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(
        useCase.getFileContentWithMetrics(projectId, command), HttpStatus.OK);
  }
}

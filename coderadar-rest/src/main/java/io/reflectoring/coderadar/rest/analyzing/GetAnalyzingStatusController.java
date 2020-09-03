package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.GetAnalyzingStatusUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetAnalyzingStatusResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class GetAnalyzingStatusController implements AbstractBaseController {
  private final GetAnalyzingStatusUseCase getAnalyzingStatusUseCase;
  private final AuthenticationService authenticationService;

  public GetAnalyzingStatusController(
      GetAnalyzingStatusUseCase getAnalyzingStatusUseCase,
      AuthenticationService authenticationService) {
    this.getAnalyzingStatusUseCase = getAnalyzingStatusUseCase;
    this.authenticationService = authenticationService;
  }

  @GetMapping(
      path = "/projects/{projectId}/analyzingStatus",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetAnalyzingStatusResponse> getProjectAnalyzingStatus(
      @PathVariable("projectId") long projectId) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(
        new GetAnalyzingStatusResponse(getAnalyzingStatusUseCase.getStatus(projectId)),
        HttpStatus.OK);
  }
}

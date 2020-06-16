package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class StartAnalyzingController implements AbstractBaseController {
  private final StartAnalyzingUseCase startAnalyzingUseCase;
  private final AuthenticationService authenticationService;

  public StartAnalyzingController(
      StartAnalyzingUseCase startAnalyzingUseCase, AuthenticationService authenticationService) {
    this.startAnalyzingUseCase = startAnalyzingUseCase;
    this.authenticationService = authenticationService;
  }

  @PostMapping(
      path = "projects/{projectId}/{branchName}/analyze",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> startAnalyzing(
      @PathVariable("projectId") long projectId, @PathVariable("branchName") String branchName) {
    authenticationService.authenticateMember(projectId);
    startAnalyzingUseCase.start(projectId, branchName);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

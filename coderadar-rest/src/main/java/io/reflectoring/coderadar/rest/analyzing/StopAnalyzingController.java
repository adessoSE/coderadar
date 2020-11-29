package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.AnalysisNotRunningException;
import io.reflectoring.coderadar.analyzer.port.driver.StopAnalyzingUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StopAnalyzingController implements AbstractBaseController {
  private final StopAnalyzingUseCase stopAnalyzingUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(path = "projects/{projectId}/stopAnalysis")
  public ResponseEntity<HttpStatus> stopAnalyzing(@PathVariable("projectId") long projectId) {
    authenticationService.authenticateMember(projectId);
    try {
      stopAnalyzingUseCase.stop(projectId);
    } catch (AnalysisNotRunningException e) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

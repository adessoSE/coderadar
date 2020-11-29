package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteAnalyzerConfigurationController implements AbstractBaseController {
  private final DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase;
  private final AuthenticationService authenticationService;

  @DeleteMapping(path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}")
  public ResponseEntity<HttpStatus> deleteAnalyzerConfiguration(
      @PathVariable("analyzerConfigurationId") long analyzerConfigurationId,
      @PathVariable("projectId") long projectId) {
    authenticationService.authenticateAdmin(projectId);
    deleteAnalyzerConfigurationUseCase.deleteAnalyzerConfiguration(
        analyzerConfigurationId, projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

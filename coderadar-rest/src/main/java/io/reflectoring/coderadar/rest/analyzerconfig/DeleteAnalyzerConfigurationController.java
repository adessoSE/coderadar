package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class DeleteAnalyzerConfigurationController implements AbstractBaseController {
  private final DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase;
  private final AuthenticationService authenticationService;

  public DeleteAnalyzerConfigurationController(
      DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase,
      AuthenticationService authenticationService) {
    this.deleteAnalyzerConfigurationUseCase = deleteAnalyzerConfigurationUseCase;
    this.authenticationService = authenticationService;
  }

  @DeleteMapping(path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}")
  public ResponseEntity<HttpStatus> deleteAnalyzerConfiguration(
      @PathVariable("analyzerConfigurationId") long analyzerConfigurationId,
      @PathVariable("projectId") long projectId) {
    deleteAnalyzerConfigurationUseCase.deleteAnalyzerConfiguration(
        analyzerConfigurationId, projectId);
    authenticationService.authenticateAdmin(projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

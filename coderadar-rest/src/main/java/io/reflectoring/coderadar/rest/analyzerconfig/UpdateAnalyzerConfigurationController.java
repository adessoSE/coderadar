package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.plugin.api.AnalyzerConfigurationException;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class UpdateAnalyzerConfigurationController implements AbstractBaseController {
  private final UpdateAnalyzerConfigurationUseCase updateAnalyzerConfigurationUseCase;
  private final AuthenticationService authenticationService;

  public UpdateAnalyzerConfigurationController(
      UpdateAnalyzerConfigurationUseCase updateAnalyzerConfigurationUseCase,
      AuthenticationService authenticationService) {
    this.updateAnalyzerConfigurationUseCase = updateAnalyzerConfigurationUseCase;
    this.authenticationService = authenticationService;
  }

  @PostMapping(
      path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> updateAnalyzerConfiguration(
      @RequestBody @Validated UpdateAnalyzerConfigurationCommand command,
      @PathVariable(name = "analyzerConfigurationId") long analyzerConfigurationId,
      @PathVariable(name = "projectId") long projectId) {
    authenticationService.authenticateAdmin(projectId);
    try {
      updateAnalyzerConfigurationUseCase.update(command, analyzerConfigurationId, projectId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (AnalyzerConfigurationException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.CONFLICT);
    }
  }
}

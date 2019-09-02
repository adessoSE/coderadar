package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class UpdateAnalyzerConfigurationController {
  private final UpdateAnalyzerConfigurationUseCase updateAnalyzerConfigurationUseCase;

  @Autowired
  public UpdateAnalyzerConfigurationController(
      UpdateAnalyzerConfigurationUseCase updateAnalyzerConfigurationUseCase) {
    this.updateAnalyzerConfigurationUseCase = updateAnalyzerConfigurationUseCase;
  }

  @PostMapping(path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}")
  public ResponseEntity updateAnalyzerConfiguration(
      @RequestBody @Validated UpdateAnalyzerConfigurationCommand command,
      @PathVariable(name = "analyzerConfigurationId") Long analyzerConfigurationId) {
      updateAnalyzerConfigurationUseCase.update(command, analyzerConfigurationId);
      return new ResponseEntity<>(HttpStatus.OK);
  }
}

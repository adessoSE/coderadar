package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/projects/{projectId}/analyzers")
public class CreateAnalyzerConfigurationController {
  private final CreateAnalyzerConfigurationUseCase createAnalyzerConfigurationUseCase;

  @Autowired
  public CreateAnalyzerConfigurationController(
      CreateAnalyzerConfigurationUseCase addAnalyzerConfigurationUseCase) {
    this.createAnalyzerConfigurationUseCase = addAnalyzerConfigurationUseCase;
  }

  @PostMapping
  public ResponseEntity<Long> addAnalyzerConfiguration(
      @RequestBody CreateAnalyzerConfigurationCommand command, @PathVariable Long projectId) {
    return new ResponseEntity<>(
        createAnalyzerConfigurationUseCase.create(command, projectId), HttpStatus.CREATED);
  }
}

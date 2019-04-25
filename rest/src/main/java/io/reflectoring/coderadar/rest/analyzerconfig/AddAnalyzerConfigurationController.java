package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects/{projectId}/analyzers")
public class AddAnalyzerConfigurationController {
  private final AddAnalyzerConfigurationUseCase addAnalyzerConfigurationUseCase;

  @Autowired
  public AddAnalyzerConfigurationController(
      AddAnalyzerConfigurationUseCase addAnalyzerConfigurationUseCase) {
    this.addAnalyzerConfigurationUseCase = addAnalyzerConfigurationUseCase;
  }

  @PostMapping
  public ResponseEntity<Long> addAnalyzerConfiguration(
      @RequestBody AddAnalyzerConfigurationCommand command) {
    return new ResponseEntity<>(addAnalyzerConfigurationUseCase.add(command), HttpStatus.CREATED);
  }
}

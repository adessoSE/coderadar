package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAnalyzerConfigurationController {
  private final GetAnalyzerConfigurationUseCase getAnalyzerConfigurationUseCase;

  @Autowired
  public GetAnalyzerConfigurationController(
      GetAnalyzerConfigurationUseCase getAnalyzerConfigurationUseCase) {
    this.getAnalyzerConfigurationUseCase = getAnalyzerConfigurationUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}")
  public ResponseEntity<GetAnalyzerConfigurationResponse> getAnalyzerConfiguration(
      @PathVariable Long analyzerConfigurationId) {
    return new ResponseEntity<>(
        getAnalyzerConfigurationUseCase.getSingleAnalyzerConfiguration(analyzerConfigurationId),
        HttpStatus.OK);
  }
}

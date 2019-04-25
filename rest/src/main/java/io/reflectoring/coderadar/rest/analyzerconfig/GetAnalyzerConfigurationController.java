package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.GetAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects/{projectId}/analyzers")
public class GetAnalyzerConfigurationController {
  private final GetAnalyzerConfigurationUseCase getAnalyzerConfigurationUseCase;

  @Autowired
  public GetAnalyzerConfigurationController(
      GetAnalyzerConfigurationUseCase getAnalyzerConfigurationUseCase) {
    this.getAnalyzerConfigurationUseCase = getAnalyzerConfigurationUseCase;
  }

  @GetMapping("/{analyzerConfigurationId}")
  public ResponseEntity<AnalyzerConfiguration> getAnalyzerConfiguration(
      @PathVariable Long analyzerConfigurationId) {
    return new ResponseEntity<>(
        getAnalyzerConfigurationUseCase.getSingleAnalyzerConfiguration(analyzerConfigurationId),
        HttpStatus.OK);
  }
}

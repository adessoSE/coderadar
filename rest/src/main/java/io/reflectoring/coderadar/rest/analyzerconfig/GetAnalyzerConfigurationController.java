package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
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
  public ResponseEntity getAnalyzerConfiguration(@PathVariable Long analyzerConfigurationId) {
    try {
      return new ResponseEntity<>(
          getAnalyzerConfigurationUseCase.getSingleAnalyzerConfiguration(analyzerConfigurationId),
          HttpStatus.OK);
    } catch (AnalyzerConfigurationNotFoundException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}

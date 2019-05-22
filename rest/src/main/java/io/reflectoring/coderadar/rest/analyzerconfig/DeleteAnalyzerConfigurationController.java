package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteAnalyzerConfigurationController {
  private final DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase;

  @Autowired
  public DeleteAnalyzerConfigurationController(
      DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase) {
    this.deleteAnalyzerConfigurationUseCase = deleteAnalyzerConfigurationUseCase;
  }

  @DeleteMapping(path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}")
  public ResponseEntity deleteAnalyzerConfiguration(
      @PathVariable Long analyzerConfigurationId) {
    try {
      deleteAnalyzerConfigurationUseCase.deleteAnalyzerConfiguration(analyzerConfigurationId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (AnalyzerConfigurationNotFoundException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}

package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class DeleteAnalyzerConfigurationController {
  private final DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase;

  @Autowired
  public DeleteAnalyzerConfigurationController(
      DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase) {
    this.deleteAnalyzerConfigurationUseCase = deleteAnalyzerConfigurationUseCase;
  }

  @DeleteMapping(path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity deleteAnalyzerConfiguration(@PathVariable("analyzerConfigurationId") Long analyzerConfigurationId, @PathVariable("projectId") Long projectId) {
      deleteAnalyzerConfigurationUseCase.deleteAnalyzerConfiguration(analyzerConfigurationId, projectId);
      return new ResponseEntity<>(HttpStatus.OK);
  }
}

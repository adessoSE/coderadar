package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationsFromProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListAnalyzerConfigurationsFromProjectController {
  private final GetAnalyzerConfigurationsFromProjectUseCase
      getAnalyzerConfigurationsFromProjectUseCase;

  @Autowired
  public ListAnalyzerConfigurationsFromProjectController(
      GetAnalyzerConfigurationsFromProjectUseCase getAnalyzerConfigurationsFromProjectUseCase) {
    this.getAnalyzerConfigurationsFromProjectUseCase = getAnalyzerConfigurationsFromProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/analyzers")
  public ResponseEntity getAnalyzerConfigurationsFromProject(@PathVariable Long projectId) {
      List<GetAnalyzerConfigurationResponse> analyzerConfigurations =
          getAnalyzerConfigurationsFromProjectUseCase.get(projectId);
      return new ResponseEntity<>(analyzerConfigurations, HttpStatus.OK);
  }
}

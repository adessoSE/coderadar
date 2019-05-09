package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationsFromProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAnalyzerConfigurationsFromProjectController {
  private final GetAnalyzerConfigurationsFromProjectUseCase
      getAnalyzerConfigurationsFromProjectUseCase;

  @Autowired
  public GetAnalyzerConfigurationsFromProjectController(
      GetAnalyzerConfigurationsFromProjectUseCase getAnalyzerConfigurationsFromProjectUseCase) {
    this.getAnalyzerConfigurationsFromProjectUseCase = getAnalyzerConfigurationsFromProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/analyzers")
  public ResponseEntity<List<GetAnalyzerConfigurationResponse>>
      getAnalyzerConfigurationsFromProject(@PathVariable Long projectId) {
    List<GetAnalyzerConfigurationResponse> analyzerConfigurations =
        getAnalyzerConfigurationsFromProjectUseCase.get(projectId);
    return new ResponseEntity<>(analyzerConfigurations, HttpStatus.OK);
  }
}

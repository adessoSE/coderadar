package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.GetAnalyzerConfigurationsFromProjectUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/projects/{projectId}/analyzers")
public class GetAnalyzerConfigurationsFromProjectController {
  private final GetAnalyzerConfigurationsFromProjectUseCase
      getAnalyzerConfigurationsFromProjectUseCase;

  @Autowired
  public GetAnalyzerConfigurationsFromProjectController(
      GetAnalyzerConfigurationsFromProjectUseCase getAnalyzerConfigurationsFromProjectUseCase) {
    this.getAnalyzerConfigurationsFromProjectUseCase = getAnalyzerConfigurationsFromProjectUseCase;
  }

  @GetMapping
  public ResponseEntity<List<AnalyzerConfiguration>> getAnalyzerConfigurationsFromProject(
      @PathVariable Long projectId) {
    List<AnalyzerConfiguration> analyzerConfigurations =
        getAnalyzerConfigurationsFromProjectUseCase.get(projectId);
    return new ResponseEntity<>(analyzerConfigurations, HttpStatus.OK);
  }
}

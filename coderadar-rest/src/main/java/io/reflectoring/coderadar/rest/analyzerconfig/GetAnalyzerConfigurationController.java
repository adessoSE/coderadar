package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetAnalyzerConfigurationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class GetAnalyzerConfigurationController extends AbstractBaseController {
  private final GetAnalyzerConfigurationUseCase getAnalyzerConfigurationUseCase;

  public GetAnalyzerConfigurationController(
      GetAnalyzerConfigurationUseCase getAnalyzerConfigurationUseCase) {
    this.getAnalyzerConfigurationUseCase = getAnalyzerConfigurationUseCase;
  }

  @GetMapping(
      path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetAnalyzerConfigurationResponse> getAnalyzerConfiguration(
      @PathVariable long analyzerConfigurationId) {
    AnalyzerConfiguration analyzerConfiguration =
        getAnalyzerConfigurationUseCase.getAnalyzerConfiguration(analyzerConfigurationId);
    return new ResponseEntity<>(
        new GetAnalyzerConfigurationResponse(
            analyzerConfiguration.getId(),
            analyzerConfiguration.getAnalyzerName(),
            analyzerConfiguration.isEnabled()),
        HttpStatus.OK);
  }
}

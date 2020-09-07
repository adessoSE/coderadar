package io.reflectoring.coderadar.rest.analyzerconfig;

import static io.reflectoring.coderadar.rest.GetAnalyzerConfigurationResponseMapper.mapAnalyzerConfiguration;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class GetAnalyzerConfigurationController implements AbstractBaseController {
  private final GetAnalyzerConfigurationUseCase getAnalyzerConfigurationUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(
      path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetAnalyzerConfigurationResponse> getAnalyzerConfiguration(
      @PathVariable long projectId, @PathVariable long analyzerConfigurationId) {
    AnalyzerConfiguration analyzerConfiguration =
        getAnalyzerConfigurationUseCase.getAnalyzerConfiguration(analyzerConfigurationId);
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(mapAnalyzerConfiguration(analyzerConfiguration), HttpStatus.OK);
  }
}

package io.reflectoring.coderadar.rest.analyzerconfig;

import static io.reflectoring.coderadar.rest.GetAnalyzerConfigurationResponseMapper.mapAnalyzerConfigurations;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.ListAnalyzerConfigurationsUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.List;
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
public class ListAnalyzerConfigurationsFromProjectController implements AbstractBaseController {
  private final ListAnalyzerConfigurationsUseCase listAnalyzerConfigurationsUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(path = "/projects/{projectId}/analyzers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetAnalyzerConfigurationResponse>>
      getAnalyzerConfigurationsFromProject(@PathVariable long projectId) {
    authenticationService.authenticateMember(projectId);
    List<AnalyzerConfiguration> analyzerConfigurations =
        listAnalyzerConfigurationsUseCase.get(projectId);
    return new ResponseEntity<>(mapAnalyzerConfigurations(analyzerConfigurations), HttpStatus.OK);
  }
}

package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.ListAnalyzerConfigurationsUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetAnalyzerConfigurationResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class ListAnalyzerConfigurationsFromProjectController extends AbstractBaseController {
  private final ListAnalyzerConfigurationsUseCase listAnalyzerConfigurationsUseCase;

  public ListAnalyzerConfigurationsFromProjectController(
      ListAnalyzerConfigurationsUseCase listAnalyzerConfigurationsUseCase) {
    this.listAnalyzerConfigurationsUseCase = listAnalyzerConfigurationsUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/analyzers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetAnalyzerConfigurationResponse>>
      getAnalyzerConfigurationsFromProject(@PathVariable long projectId) {
    return new ResponseEntity<>(
        listAnalyzerConfigurationsUseCase.get(projectId).stream()
            .map(
                analyzerConfiguration ->
                    new GetAnalyzerConfigurationResponse(
                        analyzerConfiguration.getId(),
                        analyzerConfiguration.getAnalyzerName(),
                        analyzerConfiguration.isEnabled()))
            .collect(Collectors.toList()),
        HttpStatus.OK);
  }
}

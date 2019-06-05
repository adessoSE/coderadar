package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartAnalyzingController {
  private final StartAnalyzingUseCase startAnalyzingUseCase;

  @Autowired
  public StartAnalyzingController(StartAnalyzingUseCase startAnalyzingUseCase) {
    this.startAnalyzingUseCase = startAnalyzingUseCase;
  }

  @PostMapping(path = "projects/{projectId}/analyze")
  public void startAnalyze(
      @PathVariable("projectId") Long projectId,
      @Validated @RequestBody StartAnalyzingCommand command) {

    startAnalyzingUseCase.start(command, projectId);
  }
}

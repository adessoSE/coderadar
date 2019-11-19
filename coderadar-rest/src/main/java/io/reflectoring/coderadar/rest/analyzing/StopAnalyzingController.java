package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.StopAnalyzingUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class StopAnalyzingController {
  private final StopAnalyzingUseCase stopAnalyzingUseCase;

  public StopAnalyzingController(StopAnalyzingUseCase stopAnalyzingUseCase) {
    this.stopAnalyzingUseCase = stopAnalyzingUseCase;
  }
}

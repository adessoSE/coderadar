package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.GetAnalyzingStatusUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAnalyzingStatusController {
  private final GetAnalyzingStatusUseCase getAnalyzingStatusUseCase;

  @Autowired
  public GetAnalyzingStatusController(GetAnalyzingStatusUseCase getAnalyzingStatusUseCase) {
    this.getAnalyzingStatusUseCase = getAnalyzingStatusUseCase;
  }
}

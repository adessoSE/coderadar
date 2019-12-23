package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.ListAnalyzerUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class ListAnalyzerController {
  private final ListAnalyzerUseCase listAnalyzerUseCase;

  public ListAnalyzerController(ListAnalyzerUseCase listAnalyzerUseCase) {
    this.listAnalyzerUseCase = listAnalyzerUseCase;
  }

  @GetMapping(path = "/analyzers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<String>> listAvailableAnalyzers() {
    return new ResponseEntity<>(listAnalyzerUseCase.listAvailableAnalyzers(), HttpStatus.OK);
  }
}

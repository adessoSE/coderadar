package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.port.driver.ListAnalyzerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ListAnalyzerService")
public class ListAnalyzerService implements ListAnalyzerUseCase {
  @Autowired private AnalyzerService analyzerService;

  @Override
  public List<String> listAvailableAnalyzers() {
    return analyzerService.getAvailableAnalyzers();
  }
}

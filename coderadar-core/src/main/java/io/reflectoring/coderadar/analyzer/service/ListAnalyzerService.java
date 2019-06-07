package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driver.ListAnalyzerUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListAnalyzerService implements ListAnalyzerUseCase {

  private AnalyzerPluginService analyzerPluginService;

  @Autowired
  public ListAnalyzerService(AnalyzerPluginService analyzerPluginService) {
    this.analyzerPluginService = analyzerPluginService;
  }

  @Override
  public List<String> listAvailableAnalyzers() {
    return analyzerPluginService.getAvailableAnalyzers();
  }
}

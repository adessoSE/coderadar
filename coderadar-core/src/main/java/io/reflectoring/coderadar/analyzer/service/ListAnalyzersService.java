package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driver.ListAnalyzersUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListAnalyzersService implements ListAnalyzersUseCase {

  private AnalyzerPluginService analyzerPluginService;

  @Autowired
  public ListAnalyzersService(AnalyzerPluginService analyzerPluginService) {
    this.analyzerPluginService = analyzerPluginService;
  }

  @Override
  public List<String> listAvailableAnalyzers() {
    return analyzerPluginService.getAvailableAnalyzers();
  }
}

package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driver.ListAnalyzersUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListAnalyzersService implements ListAnalyzersUseCase {

  private final AnalyzerPluginService analyzerPluginService;

  @Override
  public List<String> listAvailableAnalyzers() {
    return analyzerPluginService.getAvailableAnalyzers();
  }
}

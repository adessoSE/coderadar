package io.reflectoring.coderadar.analyzer.port.driver;

import java.util.List;

public interface ListAnalyzersUseCase {

  /**
   * Lists all available analyzers in coderadar.
   *
   * @return all available analyzers in coderadar.
   */
  List<String> listAvailableAnalyzers();
}

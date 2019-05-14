package io.reflectoring.coderadar.core.projectadministration;

public class AnalyzerConfigurationNotFoundException extends RuntimeException {
  public AnalyzerConfigurationNotFoundException(Long analyzerConfigId) {
    super("Analyzer configuration with id " + analyzerConfigId + " not found.");
  }
}

package io.reflectoring.coderadar.core.projectadministration;

public class AnalyzerConfigurationNotFoundException extends RuntimeException {
  public AnalyzerConfigurationNotFoundException(Long analyzerConfigId) {
    super("AnalyzerConfiguration with id " + analyzerConfigId + " not found.");
  }
}

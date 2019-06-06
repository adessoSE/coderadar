package io.reflectoring.coderadar.projectadministration;

public class AnalyzerConfigurationNotFoundException extends RuntimeException {
  public AnalyzerConfigurationNotFoundException(Long analyzerConfigId) {
    super("AnalyzerConfiguration with id " + analyzerConfigId + " not found.");
  }
}

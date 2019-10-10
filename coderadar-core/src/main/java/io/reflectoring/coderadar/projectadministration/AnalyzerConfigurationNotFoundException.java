package io.reflectoring.coderadar.projectadministration;

public class AnalyzerConfigurationNotFoundException extends EntityNotFoundException {
  public AnalyzerConfigurationNotFoundException(Long analyzerConfigId) {
    super("AnalyzerConfiguration with id " + analyzerConfigId + " not found.");
  }
}

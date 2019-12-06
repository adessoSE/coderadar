package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityNotFoundException;

public class AnalyzerConfigurationNotFoundException extends EntityNotFoundException {
  public AnalyzerConfigurationNotFoundException(Long analyzerConfigId) {
    super("AnalyzerConfiguration with id " + analyzerConfigId + " not found.");
  }
}

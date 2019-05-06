package io.reflectoring.coderadar.core.projectadministration;

public class AnalyzerConfigurationNotFoundException extends RuntimeException {
  public AnalyzerConfigurationNotFoundException() {
  }

  public AnalyzerConfigurationNotFoundException(String message) {
    super(message);
  }
}

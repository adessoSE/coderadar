package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityNotFoundException;

public class AnalyzerNotFoundException extends EntityNotFoundException {
  public AnalyzerNotFoundException(String name) {
    super(String.format("Analyzer with name %s not found", name));
  }
}

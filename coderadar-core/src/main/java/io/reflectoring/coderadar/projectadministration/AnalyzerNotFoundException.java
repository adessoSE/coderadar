package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityNotFoundException;

public class AnalyzerNotFoundException extends EntityNotFoundException {

  public AnalyzerNotFoundException(String msg) {
    super(msg);
  }
}

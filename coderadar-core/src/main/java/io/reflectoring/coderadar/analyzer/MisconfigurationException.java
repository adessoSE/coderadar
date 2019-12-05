package io.reflectoring.coderadar.analyzer;

/** To be used whenever a project has been misconfigured in some way. */
public class MisconfigurationException extends RuntimeException {
  public MisconfigurationException(String s) {
    super(s);
  }
}

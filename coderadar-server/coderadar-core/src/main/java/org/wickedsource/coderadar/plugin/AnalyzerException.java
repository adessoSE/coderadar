package org.wickedsource.coderadar.plugin;

/** This exception is thrown when an analyzer encounters some kind of error. */
public class AnalyzerException extends RuntimeException {

  public AnalyzerException(Throwable cause) {
    super(cause);
  }

  public AnalyzerException(String message) {
    super(message);
  }

  public AnalyzerException(String message, Throwable cause) {
    super(message, cause);
  }
}

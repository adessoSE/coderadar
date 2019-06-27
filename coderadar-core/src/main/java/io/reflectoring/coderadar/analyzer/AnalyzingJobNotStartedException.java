package io.reflectoring.coderadar.analyzer;

public class AnalyzingJobNotStartedException extends RuntimeException {
  public AnalyzingJobNotStartedException() {}

  public AnalyzingJobNotStartedException(String message) {
    super(message);
  }
}

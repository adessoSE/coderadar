package org.wickedsource.coderadar.core.configuration;

public class ConfigurationException extends RuntimeException {

  public ConfigurationException(int errorCount) {
    super(
        String.format(
            "There are %d problems with configuration parameters that prevent coderadar from starting! See error logs above for details.",
            errorCount));
  }
}

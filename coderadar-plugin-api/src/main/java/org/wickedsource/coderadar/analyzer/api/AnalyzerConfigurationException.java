package org.wickedsource.coderadar.analyzer.api;

/**
 * This exception is thrown during configuration of an analyzer.
 */
public class AnalyzerConfigurationException extends AnalyzerException {

    public AnalyzerConfigurationException(Throwable cause) {
        super(cause);
    }

    public AnalyzerConfigurationException(String message) {
        super(message);
    }

    public AnalyzerConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}

package io.reflectoring.coderadar.core.analyzer;

public class AnalyzingJobNotStartedException extends RuntimeException {
    public AnalyzingJobNotStartedException() {
    }

    public AnalyzingJobNotStartedException(String message) {
        super(message);
    }
}

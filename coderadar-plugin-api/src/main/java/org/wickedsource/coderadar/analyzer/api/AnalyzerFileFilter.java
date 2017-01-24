package org.wickedsource.coderadar.analyzer.api;

/**
 * A filter with which an AnalyzerPlugin controls which files it receives for analysis.
 */
public interface AnalyzerFileFilter {

    boolean acceptFilename(String filename);

}

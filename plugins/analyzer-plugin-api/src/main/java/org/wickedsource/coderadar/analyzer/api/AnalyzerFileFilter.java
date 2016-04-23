package org.wickedsource.coderadar.analyzer.api;

/**
 * A filter with which an AnalyzerPlugin controls which files it receives for analysis.
 */
public interface AnalyzerFileFilter {

    public boolean acceptFilename(String filename);

    public boolean acceptBinary();

}

package org.wickedsource.coderadar.analyzer.plugin.api;

/**
 * A filter with which an AnalyzerPlugin controls which files it receives for analysis.
 */
public interface AnalyzerFilter {

    public boolean acceptFilename(String filename);

    public boolean acceptBinary();

}

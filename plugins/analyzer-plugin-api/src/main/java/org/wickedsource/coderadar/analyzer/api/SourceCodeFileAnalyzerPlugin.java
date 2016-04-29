package org.wickedsource.coderadar.analyzer.api;

/**
 * Interface for analyzer plugins that analyze source code files.
 */
public interface SourceCodeFileAnalyzerPlugin extends AnalyzerPlugin {

    /**
     * Returns a filter that defines which files the analyzer is interested in. Only the files that pass this filter
     * will be passed into analyzeFile().
     *
     * @return a filter defining which files the analyzer wants to analyze.
     */
    AnalyzerFileFilter getFilter();

    /**
     * Analyzes a single file.
     *
     * @param fileContent the content of the file.
     * @return a set of metric values calculated for the given file.
     */
    FileMetrics analyzeFile(byte[] fileContent) throws AnalyzerException;

}

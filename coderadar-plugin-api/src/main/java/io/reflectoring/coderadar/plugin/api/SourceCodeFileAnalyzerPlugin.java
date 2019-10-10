package io.reflectoring.coderadar.plugin.api;

/**
 * Interface for analyzer plugins that analyze source code files.
 */
public interface SourceCodeFileAnalyzerPlugin extends AnalyzerPlugin {

    /**
     * Returns a filter that defines which files the analyzer is interested in. Only the files that
     * pass this filter will be passed into analyzeFile().
     *
     * @return a filter defining which files the analyzer wants to analyze.
     */
    AnalyzerFileFilter getFilter();

    /**
     * Analyzes a single file.
     *
     * @param filename    the full path of the file, starting from the VCS root.
     * @param fileContent the content of the file.
     * @return a set of metric values calculated for the given file.
     */
    FileMetrics analyzeFile(String filename, byte[] fileContent) throws AnalyzerException;
}

package org.wickedsource.coderadar.analyzer.api;

import java.util.Properties;

public interface Analyzer {

    /**
     * This method is called right after construction of the analyzer to pass in configuration parameters.
     * The configuration parameters are read from the central analyzer configuration. Only those properties
     * starting with the fully qualified name of the analyzer class are passed into this method. This method is expected
     * to throw an AnalyzerConfigurationException when any configuration parameter within the passed properties
     * is invalid.
     *
     * @param properties the properties targeted at this analyzer (i.e. the properties whose names start with the
     *                   fully qualified name of the analyzer class).
     * @throws AnalyzerConfigurationException in case of an error during configuration (i.e. when some configuration parameter is invalid).
     */
    void configure(Properties properties) throws AnalyzerConfigurationException;

    /**
     * Returns a filter that defines which files the analyzer is interested in. Only the files that pass this filter
     * will be passed into analyzeFile().
     *
     * @return a filter defining which files the analyzer wants to analyze.
     */
    AnalyzerFilter getFilter();

    /**
     * Analyzes a single file.
     *
     * @param fileContent the content of the file.
     * @return a set of metric values calculated for the given file.
     */
    FileMetrics analyzeFile(byte[] fileContent) throws AnalyzerException;

}

package org.wickedsource.coderadar.analyzer.api;

import java.util.Properties;

public interface Analyzer {

    /**
     * This method is called right after construction of the plugin to pass in configuration parameters.
     * The configuration parameters are read from the central analyzer configuration. Only those properties
     * starting with the fully qualified name of the plugin class are passed into this method.
     *
     * @param properties the properties targeted at this plugin (i.e. the properties whose names start with the
     *                   fully qualified name of the plugin class).
     */
    void configure(Properties properties);

    /**
     * Returns a filter that defines which files the plugin is interested in. Only the files that pass this filter
     * will be passed into analyzeFile().
     *
     * @return a filter defining which files the plugin wants to analyze.
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

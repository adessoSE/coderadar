package org.wickedsource.coderadar.analyzer.api;

import java.util.Properties;

public interface AnalyzerPlugin {
    /**
     * This method is called right after construction of the analyzer to pass in configuration parameters.
     * The configuration parameters are read from the central analyzer configuration. Only those properties
     * starting with the fully qualified name of the analyzer class are passed into this method. This method is expected
     * to throw an AnalyzerConfigurationException when any configuration parameter within the passed properties
     * is invalid.
     *
     * @param properties the properties targeted at this analyzer (i.e. the properties whose names start with the
     *                   fully qualified name of the analyzer class).
     * @throws org.wickedsource.coderadar.analyzer.api.AnalyzerConfigurationException in case of an error during configuration (i.e. when some configuration parameter is invalid).
     */
    void configure(Properties properties) throws AnalyzerConfigurationException;

    /**
     * This method is called after all files have been analyzed and can be used to free resources.
     */
    void destroy();
}

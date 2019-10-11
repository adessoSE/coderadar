package io.reflectoring.coderadar.plugin.api;

/**
 * This interface can be implemented by AnalyzerPlugin to mark them as configurable, meaning that
 * coderadar users may provide a configuration file to configure the analyzer before it starts
 * analysis. However, even if an analyzer implements this interface, it must be able to work without
 * being passed a configuration file by using a sensible default configuration.
 */
public interface ConfigurableAnalyzerPlugin {

    /**
     * This method validates if the byte array passed into the method is a valid configuration file
     * for this analyzer.
     *
     * @param configurationFile the configuration file to validate.
     * @return true if the configuration file is valid, false if not.
     */
    boolean isValidConfigurationFile(byte[] configurationFile);

    /**
     * This method is called by coderadar before the analyzeFile() method is called. It is called only
     * once during the lifetime of the analyzer plugin object. This method is only called after the
     * configuration file has been validated against the isValidConfigurationFile() method.
     *
     * @param configurationFile the configuration file to configure the analyzer plugin.
     */
    void configure(byte[] configurationFile);
}

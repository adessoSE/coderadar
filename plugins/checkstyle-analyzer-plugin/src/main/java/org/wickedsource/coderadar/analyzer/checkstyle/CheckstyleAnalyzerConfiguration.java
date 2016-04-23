package org.wickedsource.coderadar.analyzer.checkstyle;

import org.wickedsource.coderadar.analyzer.api.AnalyzerConfigurationException;

import java.io.File;
import java.util.Properties;

public class CheckstyleAnalyzerConfiguration {

    private static final String PARAM_CONFIG_LOCATION = "configLocation";

    private Properties backingProperties;

    private File configLocation;

    public CheckstyleAnalyzerConfiguration(Properties backingProperties) throws AnalyzerConfigurationException {
        this.backingProperties = backingProperties;
        initializeConfigLocation(getPropertyWithoutPrefix(PARAM_CONFIG_LOCATION));
    }

    private String getPropertyWithoutPrefix(String propertyName) {
        return (String) backingProperties.get(CheckstyleFileAnalyzerPlugin.class.getName() + "." + propertyName);
    }

    private void initializeConfigLocation(String configLocationPath) throws AnalyzerConfigurationException {
        if (configLocationPath == null) {
            throw new AnalyzerConfigurationException(String.format("Checkstyle configuration missing. Please configure the location to the checkstyle config file via configuration parameter %s", PARAM_CONFIG_LOCATION));
        } else {
            configLocation = new File(configLocationPath);
            if (!configLocation.exists()) {
                throw new AnalyzerConfigurationException(String.format("Checkstyle configuration file %s does not exist.", configLocationPath));
            }
        }
    }

    public File getConfigLocation() {
        return configLocation;
    }

    protected Properties getBackingProperties() {
        return backingProperties;
    }
}

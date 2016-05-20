package org.wickedsource.coderadar.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.api.AnalyzerConfigurationException;
import org.wickedsource.coderadar.analyzer.api.AnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.api.SourceCodeFileAnalyzerPlugin;

import java.util.*;

public class AnalyzerPluginRegistry {

    private Logger logger = LoggerFactory.getLogger(AnalyzerPluginRegistry.class);

    private boolean initialized = false;

    private List<SourceCodeFileAnalyzerPlugin> sourceCodeFileAnalyzerPlugins = new ArrayList<>();

    /**
     * Initializes all Analyzers that are in the classpath and registered via Java's ServiceLoader mechanism.
     *
     * @param properties The properties used to configure the analyzers. Each analyzer will be configured with the
     *                   properties whose names start with the fully qualified name of the analyzer class.
     */
    public synchronized void initializeAnalyzers(Properties properties) throws AnalyzerConfigurationException {
        if (!initialized) {
            ServiceLoader<SourceCodeFileAnalyzerPlugin> loader = ServiceLoader.load(SourceCodeFileAnalyzerPlugin.class);
            for (SourceCodeFileAnalyzerPlugin analyzerPlugin : loader) {
                logger.info("initializing analyzer plugin {}", analyzerPlugin.getClass());
                Properties propertiesForThisAnalyzer = extractPropertiesForAnalyzer(analyzerPlugin, properties);

                // only register explicitly enabled analyzer plugins
                if (isAnalyzerEnabled(analyzerPlugin, properties)) {
                    analyzerPlugin.configure(propertiesForThisAnalyzer);
                    sourceCodeFileAnalyzerPlugins.add(analyzerPlugin);
                    logger.info("successfully registered analyzer plugin {}", analyzerPlugin.getClass());
                    logger.debug("configured analyzer plugin {} with the following properties: {}", analyzerPlugin.getClass(), propertiesForThisAnalyzer);
                } else {
                    logger.info("skipped registration of analyzer plugin {}, since property {}.enabled is not set to true", analyzerPlugin.getClass().getName(), analyzerPlugin.getClass().getName());
                }
            }
            initialized = true;
        }
    }

    /**
     * Calls the destroy() method of all registered analyzers.
     */
    public synchronized void destroyAnalyzers() {
        for (AnalyzerPlugin analyzerPlugin : sourceCodeFileAnalyzerPlugins) {
            analyzerPlugin.destroy();
        }
    }

    /**
     * Extracts the properties directed at the given analyzer from the global properties
     *
     * @param analyzerPlugin   the analyzer whose properties to extract.
     * @param globalProperties the global properties.
     * @return the properties directed at the given analyzer.
     */
    private Properties extractPropertiesForAnalyzer(AnalyzerPlugin analyzerPlugin, Properties globalProperties) {
        Properties analyzerProperties = new Properties();
        for (Map.Entry entry : globalProperties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith(analyzerPlugin.getClass().getName())) {
                analyzerProperties.setProperty(key, value);
            }
        }
        return analyzerProperties;
    }

    private boolean isAnalyzerEnabled(AnalyzerPlugin analyzerPlugin, Properties properties) {
        Object enabledProperty = properties.get(analyzerPlugin.getClass().getName() + ".enabled");
        if (enabledProperty != null) {
            return Boolean.valueOf(String.valueOf(enabledProperty));
        } else {
            return false;
        }
    }

    public List<SourceCodeFileAnalyzerPlugin> getRegisteredFileAnalyzers() {
        if (!initialized) {
            throw new IllegalStateException("registry has not yet been initialized! call initialize() first!");
        }
        return this.sourceCodeFileAnalyzerPlugins;
    }

}

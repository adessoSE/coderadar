package org.wickedsource.coderadar.annotator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.api.Analyzer;
import org.wickedsource.coderadar.analyzer.api.AnalyzerConfigurationException;

import java.util.*;

public class AnalyzerRegistry {

    private Logger logger = LoggerFactory.getLogger(AnalyzerRegistry.class);

    private boolean initialized = false;

    private List<Analyzer> analyzers = new ArrayList<>();

    /**
     * Initializes all Analyzers that are in the classpath and registered via Java's ServiceLoader mechanism.
     *
     * @param properties The properties used to configure the analyzers. Each analyzer will be configured with the
     *                   properties whose names start with the fully qualified name of the analyzer class.
     */
    public synchronized void initializeAnalyzers(Properties properties) throws AnalyzerConfigurationException {
        ServiceLoader<Analyzer> loader = ServiceLoader.load(Analyzer.class);
        for (Analyzer analyzer : loader) {
            logger.info("initializing Analyzer plugin {}", analyzer.getClass());
            Properties propertiesForThisAnalyzer = extractPropertiesForAnalyzer(analyzer, properties);
            analyzer.configure(propertiesForThisAnalyzer);
            analyzers.add(analyzer);
        }
        initialized = true;
    }

    /**
     * Extracts the properties directed at the given analyzer from the global properties
     *
     * @param analyzer         the analyzer whose properties to extract.
     * @param globalProperties the global properties.
     * @return the properties directed at the given analyzer.
     */
    private Properties extractPropertiesForAnalyzer(Analyzer analyzer, Properties globalProperties) {
        Properties analyzerProperties = new Properties();
        for (Map.Entry entry : globalProperties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith(analyzer.getClass().getName())) {
                logger.debug("configuring analyzer {}: {} = {}", analyzer.getClass(), key, value);
                analyzerProperties.setProperty(key, value);
            }
        }
        return analyzerProperties;
    }

    public List<Analyzer> getRegisteredAnalyzers() {
        if (!initialized) {
            throw new IllegalStateException("Registry has not yet been initialized! Call initialize() first!");
        }
        return this.analyzers;
    }

}

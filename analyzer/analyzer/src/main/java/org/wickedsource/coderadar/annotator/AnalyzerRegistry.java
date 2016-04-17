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
            logger.info("initializing analyzer plugin {}", analyzer.getClass());
            Properties propertiesForThisAnalyzer = extractPropertiesForAnalyzer(analyzer, properties);

            // only register explicitly enabled analyzer plugins
            if (isAnalyzerEnabled(analyzer, properties)) {
                analyzer.configure(propertiesForThisAnalyzer);
                analyzers.add(analyzer);
                logger.info("successfully registered analyzer plugin {}", analyzer.getClass());
                logger.debug("configured analyzer plugin {} with the following properties: {}", analyzer.getClass(), propertiesForThisAnalyzer);
            }else{
                logger.info("skipped registration of analyzer plugin {}, since property {}.enabled is not set to true", analyzer.getClass(), analyzer.getClass());
            }
        }
        initialized = true;
    }

    /**
     * Calls the destroy() method of all registered analyzers.
     */
    public synchronized void destroyAnalyzers() {
        for (Analyzer analyzer : analyzers) {
            analyzer.destroy();
        }
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
                analyzerProperties.setProperty(key, value);
            }
        }
        return analyzerProperties;
    }

    private boolean isAnalyzerEnabled(Analyzer analyzer, Properties properties) {
        Object enabledProperty = properties.get(analyzer.getClass().getName() + ".enabled");
        if(enabledProperty != null){
            return Boolean.valueOf(String.valueOf(enabledProperty));
        }else{
            return false;
        }
    }

    public List<Analyzer> getRegisteredAnalyzers() {
        if (!initialized) {
            throw new IllegalStateException("registry has not yet been initialized! call initialize() first!");
        }
        return this.analyzers;
    }

}

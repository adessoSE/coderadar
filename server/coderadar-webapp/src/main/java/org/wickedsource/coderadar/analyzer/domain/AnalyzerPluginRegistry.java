package org.wickedsource.coderadar.analyzer.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.analyzer.api.SourceCodeFileAnalyzerPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

@Component
public class AnalyzerPluginRegistry implements InitializingBean{

    private Logger logger = LoggerFactory.getLogger(AnalyzerPluginRegistry.class);

    private Map<String, SourceCodeFileAnalyzerPlugin> sourceCodeFileAnalyzerPlugins = new HashMap<>();

    public SourceCodeFileAnalyzerPlugin getAnalyzer(String analyzerName) {
        return sourceCodeFileAnalyzerPlugins.get(analyzerName);
    }

    @Override
    public void afterPropertiesSet() {
        ServiceLoader<SourceCodeFileAnalyzerPlugin> loader = ServiceLoader.load(SourceCodeFileAnalyzerPlugin.class);
        for (SourceCodeFileAnalyzerPlugin analyzerPlugin : loader) {
            sourceCodeFileAnalyzerPlugins.put(analyzerPlugin.getName(), analyzerPlugin);
            logger.info("registered analyzer plugin {}", analyzerPlugin.getClass());
        }
    }
}

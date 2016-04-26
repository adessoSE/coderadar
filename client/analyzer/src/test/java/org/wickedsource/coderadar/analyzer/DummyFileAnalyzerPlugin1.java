package org.wickedsource.coderadar.analyzer;

import org.wickedsource.coderadar.analyzer.api.*;

import java.util.Properties;

public class DummyFileAnalyzerPlugin1 implements FileAnalyzerPlugin {

    private Properties properties;

    @Override
    public void configure(Properties properties) {
        this.properties = properties;
    }

    @Override
    public AnalyzerFileFilter getFilter() {
        return new DefaultFileFilter();
    }

    @Override
    public FileMetrics analyzeFile(byte[] fileContent) throws AnalyzerException {
        return new FileMetrics();
    }

    @Override
    public void destroy() {
        // nothing to destroy
    }

    public Properties getProperties() {
        return this.properties;
    }
}

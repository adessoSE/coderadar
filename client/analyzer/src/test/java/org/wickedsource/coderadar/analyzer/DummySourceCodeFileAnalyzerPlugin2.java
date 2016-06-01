package org.wickedsource.coderadar.analyzer;

import org.wickedsource.coderadar.analyzer.api.*;

import java.util.Properties;

public class DummySourceCodeFileAnalyzerPlugin2 implements SourceCodeFileAnalyzerPlugin {

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
    public void releaseResources() {
        // nothing to releaseResources
    }

    public Properties getProperties() {
        return this.properties;
    }
}

package org.wickedsource.coderadar.factories.entities;

import org.wickedsource.coderadar.analyzer.api.*;

public class AnalyzerFactory {

    public ConfigurableAnalyzerPlugin configurableAnalyzer() {
        return new DummyAnalyzer();
    }

    private class DummyAnalyzer implements SourceCodeFileAnalyzerPlugin, ConfigurableAnalyzerPlugin {

        @Override
        public boolean isValidConfigurationFile(byte[] configurationFile) {
            return true;
        }

        @Override
        public void configure(byte[] configurationFile) {

        }

        @Override
        public AnalyzerFileFilter getFilter() {
            return new DefaultFileFilter();
        }

        @Override
        public FileMetrics analyzeFile(byte[] fileContent) throws AnalyzerException {
            return new FileMetrics();
        }
    }
}

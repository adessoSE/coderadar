package org.wickedsource.coderadar.annotator;

import org.wickedsource.coderadar.analyzer.api.Analyzer;
import org.wickedsource.coderadar.analyzer.api.AnalyzerException;
import org.wickedsource.coderadar.analyzer.api.AnalyzerFilter;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;

import java.util.Properties;

public class DummyAnalyzer1 implements Analyzer {

    private Properties properties;

    @Override
    public void configure(Properties properties) {
        this.properties = properties;
    }

    @Override
    public AnalyzerFilter getFilter() {
        return null;
    }

    @Override
    public FileMetrics analyzeFile(byte[] fileContent) throws AnalyzerException {
        return null;
    }

    public Properties getProperties() {
        return this.properties;
    }
}

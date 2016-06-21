package org.wickedsource.coderadar.factories;

import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfiguration;

public class AnalyzerConfigurationFactory {

    public AnalyzerConfiguration analyzerConfiguration(){
        AnalyzerConfiguration configuration = new AnalyzerConfiguration();
        configuration.setAnalyzerName("org.wickedsource.locAnalyzer");
        configuration.setEnabled(Boolean.TRUE);
        configuration.setId(1L);
        return configuration;
    }

    public AnalyzerConfiguration analyzerConfiguration2(){
        AnalyzerConfiguration configuration = new AnalyzerConfiguration();
        configuration.setAnalyzerName("org.wickedsource.fooAnalyzer");
        configuration.setEnabled(Boolean.FALSE);
        configuration.setId(2L);
        return configuration;
    }
}

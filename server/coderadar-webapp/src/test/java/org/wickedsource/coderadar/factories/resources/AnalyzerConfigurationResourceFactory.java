package org.wickedsource.coderadar.factories.resources;


import org.wickedsource.coderadar.analyzer.rest.AnalyzerConfigurationResource;

public class AnalyzerConfigurationResourceFactory {

    public AnalyzerConfigurationResource analyzerConfiguration(){
        AnalyzerConfigurationResource resource = new AnalyzerConfigurationResource();
        resource.setAnalyzerName("org.wickedsource.locAnalyzer");
        resource.setEnabled(Boolean.TRUE);
        return resource;
    }

}

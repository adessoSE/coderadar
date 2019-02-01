package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.analyzerconfig.rest.AnalyzerConfigurationResource;

public class AnalyzerConfigurationResourceFactory {

	public AnalyzerConfigurationResource analyzerConfiguration() {
		AnalyzerConfigurationResource resource = new AnalyzerConfigurationResource();
		resource.setAnalyzerName("org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer");
		resource.setEnabled(Boolean.TRUE);
		return resource;
	}

	public AnalyzerConfigurationResource analyzerConfiguration2() {
		AnalyzerConfigurationResource resource = new AnalyzerConfigurationResource();
		resource.setAnalyzerName("org.wickedsource.coderadar.analyzer.domain.DummyAnalyzer");
		resource.setEnabled(Boolean.FALSE);
		return resource;
	}
}

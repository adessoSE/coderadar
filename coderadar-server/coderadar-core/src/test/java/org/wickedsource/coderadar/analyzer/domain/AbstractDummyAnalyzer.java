package org.wickedsource.coderadar.analyzer.domain;

import org.wickedsource.coderadar.analyzer.api.*;

public abstract class AbstractDummyAnalyzer
		implements SourceCodeFileAnalyzerPlugin, ConfigurableAnalyzerPlugin {

	private boolean configured;

	@Override
	public AnalyzerFileFilter getFilter() {
		return new DefaultFileFilter();
	}

	@Override
	public FileMetrics analyzeFile(String filename, byte[] fileContent) throws AnalyzerException {
		return null;
	}

	@Override
	public boolean isValidConfigurationFile(byte[] configurationFile) {
		if (configurationFile[0] == 'a') {
			return true;
		}
		return false;
	}

	@Override
	public void configure(byte[] configurationFile) {
		configured = true;
	}

	public boolean isConfigured() {
		return configured;
	}
}

package org.wickedsource.coderadar.analyzer.rest;

import org.springframework.hateoas.ResourceSupport;

public class AnalyzerResource extends ResourceSupport {

	private String analyzerName;

	public AnalyzerResource(String analyzerName) {
		this.analyzerName = analyzerName;
	}

	public String getAnalyzerName() {
		return analyzerName;
	}

	public void setAnalyzerName(String analyzerName) {
		this.analyzerName = analyzerName;
	}
}

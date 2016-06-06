package org.wickedsource.coderadar.analyzer.rest;

import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

class AnalyzerConfigurationResource extends ResourceSupport {

    @NotNull
    private String analyzerName;

    @NotNull
    private Boolean enabled;

    public AnalyzerConfigurationResource(){

    }

    public AnalyzerConfigurationResource(String analyzerName, boolean enabled) {
        this.analyzerName = analyzerName;
        this.enabled = enabled;
    }

    public String getAnalyzerName() {
        return analyzerName;
    }

    public void setAnalyzerName(String analyzerName) {
        this.analyzerName = analyzerName;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}

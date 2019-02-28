package org.wickedsource.coderadar.analyzerconfig.rest;

import javax.validation.constraints.NotNull;

public class AnalyzerConfigurationResource {

  private Long id;

  @NotNull private String analyzerName;

  @NotNull private Boolean enabled;

  public AnalyzerConfigurationResource() {}

  public AnalyzerConfigurationResource(Long id, String analyzerName, boolean enabled) {
    this.id = id;
    this.analyzerName = analyzerName;
    this.enabled = enabled;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

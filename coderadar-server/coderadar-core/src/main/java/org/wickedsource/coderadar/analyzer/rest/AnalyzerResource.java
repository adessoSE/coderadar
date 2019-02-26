package org.wickedsource.coderadar.analyzer.rest;

public class AnalyzerResource {

  private Long id;

  private String analyzerName;

  public AnalyzerResource(String analyzerName) {
    this.analyzerName = analyzerName;
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
}

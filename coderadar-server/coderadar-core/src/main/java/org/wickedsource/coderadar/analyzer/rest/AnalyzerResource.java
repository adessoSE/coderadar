package org.wickedsource.coderadar.analyzer.rest;

public class AnalyzerResource {

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

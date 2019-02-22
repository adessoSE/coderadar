package org.wickedsource.coderadar.analyzer.rest;

import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;

public class AnalyzerResourceAssembler extends AbstractResourceAssembler<String, AnalyzerResource> {

  @Override
  public AnalyzerResource toResource(String analyzerName) {
    return new AnalyzerResource(analyzerName);
  }
}

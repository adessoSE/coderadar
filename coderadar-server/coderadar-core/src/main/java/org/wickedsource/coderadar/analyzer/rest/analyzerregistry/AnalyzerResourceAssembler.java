package org.wickedsource.coderadar.analyzer.rest.analyzerregistry;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class AnalyzerResourceAssembler extends ResourceAssemblerSupport<String, AnalyzerResource> {

  public AnalyzerResourceAssembler() {
    super(AnalyzerController.class, AnalyzerResource.class);
  }

  @Override
  public AnalyzerResource toResource(String analyzerName) {
    return new AnalyzerResource(analyzerName);
  }
}

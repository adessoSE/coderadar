package org.wickedsource.coderadar.analyzer.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry;

@Controller
@Transactional
@RequestMapping(path = "/analyzers")
public class AnalyzerController {

  private AnalyzerPluginRegistry analyzerRegistry;

  @Autowired
  public AnalyzerController(AnalyzerPluginRegistry analyzerRegistry) {
    this.analyzerRegistry = analyzerRegistry;
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public ResponseEntity<List<AnalyzerResource>> listAnalyzers(Pageable pageable) {
    List<String> analyzerPage = analyzerRegistry.getAvailableAnalyzers();
    AnalyzerResourceAssembler assembler = new AnalyzerResourceAssembler();
    return new ResponseEntity<>(assembler.toResourceList(analyzerPage), HttpStatus.OK);
  }
}

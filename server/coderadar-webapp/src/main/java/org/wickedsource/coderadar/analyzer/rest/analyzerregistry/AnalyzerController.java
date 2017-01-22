package org.wickedsource.coderadar.analyzer.rest.analyzerregistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerPluginRegistry;

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
  public ResponseEntity<PagedResources<AnalyzerResource>> listAnalyzers(
      @PageableDefault Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {
    Page<String> analyzerPage = analyzerRegistry.getAvailableAnalyzers(pageable);
    AnalyzerResourceAssembler assembler = new AnalyzerResourceAssembler();
    PagedResources<AnalyzerResource> pagedResources =
        pagedResourcesAssembler.toResource(analyzerPage, assembler);
    return new ResponseEntity<>(pagedResources, HttpStatus.OK);
  }
}

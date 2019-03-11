package org.wickedsource.coderadar.analyzer.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnalyzerResource extends ResourceSupport {

  private String analyzerName;
}

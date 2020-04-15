package io.reflectoring.coderadar.graph.analyzer;

import io.reflectoring.coderadar.analyzer.domain.Finding;
import io.reflectoring.coderadar.graph.Mapper;

/**
 * Findings are mapped to strings in the following format: "1-2-3-4" where 1 is lineStart, 2 is
 * lineEnd, 3 is charStart and 4 is charEnd.
 */
public class FindingsMapper implements Mapper<Finding, String> {

  @Override
  public Finding mapGraphObject(String findingsString) {
    Finding domainObject = new Finding();
    String[] values = findingsString.split("-");
    domainObject.setLineStart(Integer.parseInt(values[0]));
    domainObject.setLineEnd(Integer.parseInt(values[1]));
    domainObject.setCharStart(Integer.parseInt(values[2]));
    domainObject.setCharEnd(Integer.parseInt(values[3]));
    return domainObject;
  }

  @Override
  public String mapDomainObject(Finding domainObject) {
    return String.format(
        "%d-%d-%d-%d",
        domainObject.getLineStart(),
        domainObject.getLineEnd(),
        domainObject.getCharStart(),
        domainObject.getCharEnd());
  }
}

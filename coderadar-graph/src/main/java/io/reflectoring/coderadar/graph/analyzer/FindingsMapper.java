package io.reflectoring.coderadar.graph.analyzer;

import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.plugin.api.Finding;

/**
 * Findings are mapped to strings in the following format: "1-2-3-4" where 1 is lineStart, 2 is
 * lineEnd, 3 is charStart and 4 is charEnd.
 */
public class FindingsMapper implements Mapper<Finding, String> {

  @Override
  public Finding mapGraphObject(String findingsString) {
    String[] values = findingsString.split("-", 3);
    return new Finding(Integer.parseInt(values[0]), Integer.parseInt(values[1]), values[2]);
  }

  @Override
  public String mapDomainObject(Finding domainObject) {
    return String.format(
        "%d-%d-%s",
        domainObject.getLineStart(), domainObject.getLineEnd(), domainObject.getMessage());
  }
}

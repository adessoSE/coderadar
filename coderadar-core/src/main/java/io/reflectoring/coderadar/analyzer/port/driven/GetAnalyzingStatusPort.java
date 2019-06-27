package io.reflectoring.coderadar.analyzer.port.driven;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;

public interface GetAnalyzingStatusPort {
  boolean get(Long projectId) throws ProjectNotFoundException;
}

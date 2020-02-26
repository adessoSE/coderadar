package io.reflectoring.coderadar.dependencymap.port.driven;

import io.reflectoring.coderadar.dependencymap.domain.CompareNode;

public interface GetCompareTreePort {
  CompareNode getRoot(
      String projectRoot, String commitName, String projectName, String secondCommit);
}

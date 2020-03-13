package io.reflectoring.coderadar.dependencymap.port.driven;

import io.reflectoring.coderadar.dependencymap.domain.Node;

public interface GetDependencyTreePort {
  Node getRoot(String projectRoot, String commitName, String projectName);
}

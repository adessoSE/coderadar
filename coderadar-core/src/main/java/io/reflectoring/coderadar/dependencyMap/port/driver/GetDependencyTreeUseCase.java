package io.reflectoring.coderadar.dependencymap.port.driver;

import io.reflectoring.coderadar.dependencymap.domain.Node;

public interface GetDependencyTreeUseCase {
  Node getDependencyTree(Long projectId, String commitName);
}

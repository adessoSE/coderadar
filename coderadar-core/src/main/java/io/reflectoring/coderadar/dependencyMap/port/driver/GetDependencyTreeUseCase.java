package io.reflectoring.coderadar.dependencyMap.port.driver;

import io.reflectoring.coderadar.dependencyMap.domain.Node;

public interface GetDependencyTreeUseCase {
    Node getDependencyTree(Long projectId, String commitName);
}

package io.reflectoring.coderadar.dependencyMap.port.driven;

import io.reflectoring.coderadar.dependencyMap.domain.Node;

public interface GetDependencyTreePort {
    Node getRoot(String projectRoot, String commitName, String projectName);
}

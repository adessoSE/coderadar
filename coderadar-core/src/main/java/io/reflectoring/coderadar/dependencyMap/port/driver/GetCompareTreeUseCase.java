package io.reflectoring.coderadar.dependencyMap.port.driver;

import io.reflectoring.coderadar.dependencyMap.domain.CompareNode;

public interface GetCompareTreeUseCase {
    CompareNode getDependencyTree(Long projectId, String commitName, String secondCommit);
}

package io.reflectoring.coderadar.dependencymap.port.driver;

import io.reflectoring.coderadar.dependencymap.domain.CompareNode;

public interface GetCompareTreeUseCase {
    CompareNode getDependencyTree(Long projectId, String commitName, String secondCommit);
}

package io.reflectoring.coderadar.dependencyMap.port.driven;

import io.reflectoring.coderadar.dependencyMap.domain.CompareNode;

public interface GetCompareTreePort {
    CompareNode getRoot(String projectRoot, String commitName, String projectName, String secondCommit);
}

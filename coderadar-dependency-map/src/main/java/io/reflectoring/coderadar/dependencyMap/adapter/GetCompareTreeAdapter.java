package io.reflectoring.coderadar.analyzer.dependencyMap.adapter;

import io.reflectoring.coderadar.analyzer.dependencyMap.domain.DependencyCompareTree;
import io.reflectoring.coderadar.dependencyMap.domain.CompareNode;
import io.reflectoring.coderadar.dependencyMap.port.driven.GetCompareTreePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCompareTreeAdapter implements GetCompareTreePort {
    private final DependencyCompareTree dependencyCompareTree;

    @Autowired
    public GetCompareTreeAdapter(DependencyCompareTree dependencyCompareTree) {
        this.dependencyCompareTree = dependencyCompareTree;
    }

    @Override
    public CompareNode getRoot(String projectRoot, String commitName, String projectName, String secondCommit) {
        return dependencyCompareTree.getRoot(projectRoot, commitName, projectName, secondCommit);
    }
}

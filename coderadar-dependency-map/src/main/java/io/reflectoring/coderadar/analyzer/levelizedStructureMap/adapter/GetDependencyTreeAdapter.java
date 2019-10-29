package io.reflectoring.coderadar.analyzer.levelizedStructureMap.adapter;

import io.reflectoring.coderadar.analyzer.levelizedStructureMap.domain.DependencyTree;
import io.reflectoring.coderadar.dependencyMap.domain.Node;
import io.reflectoring.coderadar.dependencyMap.port.driven.GetDependencyTreePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetDependencyTreeAdapter implements GetDependencyTreePort {

    private final DependencyTree dependencyTree;

    @Autowired
    public GetDependencyTreeAdapter(DependencyTree dependencyTree) {
        this.dependencyTree = dependencyTree;
    }

    @Override
    public Node getRoot(String projectRoot, String commitName, String projectName) {
        return dependencyTree.getRoot(projectRoot, commitName, projectName);
    }
}

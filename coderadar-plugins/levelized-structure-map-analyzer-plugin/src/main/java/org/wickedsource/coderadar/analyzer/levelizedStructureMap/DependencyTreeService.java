package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import org.eclipse.jgit.lib.Repository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class DependencyTreeService {

    public Node getDependencyTree(Repository repository, String commitName, String basepackage, String repoName) {
        DependencyTree dependencyTree = new DependencyTree(basepackage, commitName, repository);

        Node baseRoot = new Node(new LinkedList<>(), repository.getWorkTree().getParentFile().getPath(), repoName, "");

        // TODO move to constructor or factory method
        dependencyTree.createTreeRoot(baseRoot);

        dependencyTree.setDependencies(baseRoot);
        baseRoot.setDependencies(new LinkedList<>());
        dependencyTree.sortTree(baseRoot);
        dependencyTree.setLevel(baseRoot);
        return baseRoot;
    }

    public CompareNode getCompareTree(Repository repository, String commitName, String basepackage, String repoName, String secondCommit) {
        DependencyTree dependencyTree = new DependencyTree(basepackage, commitName, repository);
        Node baseVersion = getDependencyTree(repository, commitName, basepackage, repoName);
        CompareNode compareNode = dependencyTree.createMergeTree(baseVersion);
        dependencyTree.addToMergeTree(compareNode, secondCommit);
        dependencyTree.setDependenciesForCompareNode(compareNode, secondCommit);

        dependencyTree.setCompareLayer(compareNode);

//        System.out.println(compareNode);

        return compareNode;
    }
}

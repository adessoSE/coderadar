package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import org.eclipse.jgit.lib.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;

@Service
public class DependencyTreeService {

    public Node getDependencyTree(Repository repository, String commitName, String basepackage, String repoName) {
        Node baseRoot = new Node(new ArrayList<>(), repository.getWorkTree().getParentFile().getPath(), repoName, "");
        return DependencyTree.getTree().getDependencyTree(basepackage, commitName, repository, baseRoot);
    }

    public CompareNode getCompareTree(Repository repository, String commitName, String basepackage, String repoName, String secondCommit) {
        Node baseVersion = getDependencyTree(repository, commitName, basepackage, repoName);
        return DependencyTree.getTree().getCompareTree(basepackage, commitName, repository, baseVersion, secondCommit);
    }
}

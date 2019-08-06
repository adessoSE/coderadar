package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import org.eclipse.jgit.lib.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;

@Service
public class DependencyTreeService {

    public Node getDependencyTree(Repository repository, String commitName, String repoName) {
        Node baseRoot = new Node(new ArrayList<>(), "", repoName, "");
        return DependencyTree.getTree().getDependencyTree(commitName, repository, baseRoot);
    }

    public CompareNode getCompareTree(Repository repository, String commitName, String repoName, String secondCommit) {
        Node baseVersion = getDependencyTree(repository, commitName, repoName);
        return DependencyTree.getTree().getCompareTree(commitName, repository, baseVersion, secondCommit);
    }
}

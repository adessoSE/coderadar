package io.reflectoring.coderadar.analyzer.levelizedStructureMap.domain;

import io.reflectoring.coderadar.dependencyMap.domain.Node;
import io.reflectoring.coderadar.dependencyMap.domain.NodeDTO;
import io.reflectoring.coderadar.vcs.UnableToWalkCommitTreeException;
import io.reflectoring.coderadar.vcs.port.driven.WalkCommitTreePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.reflectoring.coderadar.analyzer.levelizedStructureMap.analyzers.JavaAnalyzer;

import java.util.ArrayList;
import java.util.List;

@Service
public class DependencyTree {

    private Node root;
    private String repository;
    private String commitName;

    private final JavaAnalyzer javaAnalyzer;
    private final WalkCommitTreePort walkCommitTreePort;

    @Autowired
    public DependencyTree(JavaAnalyzer javaAnalyzer, WalkCommitTreePort walkCommitTreePort) {
        this.javaAnalyzer = javaAnalyzer;
        this.walkCommitTreePort = walkCommitTreePort;
    }

    /**
     * Creates the
     */
    private void createTree() {
        try {
            walkCommitTreePort.walkCommitTree(repository, commitName, pathString -> root.createNodeByPath(pathString));
        } catch (UnableToWalkCommitTreeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a dependency tree from a given commit in a git repo beginning with a root Node-object.
     *
     * @param projectRoot repo to analyze.
     * @param commitName commit to analyze.
     * @param repoName name of the repository.
     */
    public Node getRoot(String projectRoot, String commitName, String repoName) {
        this.repository = projectRoot;
        this.commitName = commitName;
        this.root = new Node(repoName, "", "");
        createTree();
        // traverse to set packageName and dependencies from fileContent
        this.root.traversePost(node -> {
            // set packageNames
            if (!node.hasChildren()) {
                // set this nodes and parent nodes packageName
                // set also parent.packageName to prevent trouble with .java ending
                String parentPackage = javaAnalyzer.getPackageName(node.getPath(), projectRoot, commitName);
                node.getParent(root).setPackageName(parentPackage);
                node.setPackageName(parentPackage + "." + node.getFilename());
            } else {
                if (node.getPackageName().equals("")) {
                    String childPackage = node.getChildren().get(0).getPackageName();
                    node.setPackageName(childPackage.contains(".") ? childPackage.substring(0, childPackage.lastIndexOf(".")) : "");
                }
            }

            // set dependencies
            if (node.hasChildren()) {
                node.getChildren().forEach(child -> node.addToDependencies(child.getDependencies()));
            } else {
                javaAnalyzer.getValidImportsFromFile(node.getPath(), projectRoot, commitName)
                        .forEach(importString -> getNodeFromImport(importString).stream()
                                .filter(dependency -> !node.getDependencies().contains(new NodeDTO(dependency.getPath()))
                                        && !dependency.getFilename().equals(node.getFilename()))
                                .map(dependency -> new NodeDTO(dependency.getPath()))
                                .forEach(node::addToDependencies)
                        );
            }
        });

        // traverse to compare nodes and to set their level
        NodeComparator nodeComparator = new NodeComparator();
        this.root.traversePost(node -> {
            if (node.hasChildren()) {
                node.getChildren().sort(nodeComparator);
                int level = 0;
                for (int i = 0; i < node.getChildren().size(); i++) {
                    // for every child in the current layer check
                    for (int j = 0; j < i; j++) {
                        if (node.getChildren().get(j).getLevel() == level) {
                            // if any child before this has a dependency on this
                            // or any child before has more dependencies on this than this has on any child before
                            //   raise layer, break
                            if (node.getChildren().get(j).hasDependencyOn(node.getChildren().get(i))
                                    && !node.getChildren().get(i).hasDependencyOn(node.getChildren().get(j))) {
                                level++;
                                break;
                            } else if (node.getChildren().get(j).countDependenciesOn(node.getChildren().get(i))
                                    > node.getChildren().get(i).countDependenciesOn(node.getChildren().get(j))) {
                                level++;
                                break;
                            }
                        }
                    }
                    node.getChildren().get(i).setLevel(level);
                }
            }
        });
        return root;
    }

    public List<Node> getNodeFromImport(String importString) {
        List<Node> imports = new ArrayList<>();
        root.traversePre(node -> {
            // try to find node by path(importString)
            // if not found continue traversing
            // if none found importString references dependency from outside the project
            String pathString = importString;
            if (pathString.contains("*")) {
                String localImportString = pathString.substring(0, pathString.lastIndexOf("."));
                pathString = localImportString.replace(".", "/");
                if (node.getNodeByPath(pathString) != null) {
                    Node tmp = node.getNodeByPath(pathString);
                    if (localImportString.equals(tmp.getPackageName())) {
                        imports.addAll(tmp.getChildren());
                    }
                }
            } else {
                pathString = pathString.replace(".", "/").concat(".java");
                if (node.getNodeByPath(pathString) != null) {
                    imports.add(node.getNodeByPath(pathString));
                }
            }
        });
        return imports;
    }
}

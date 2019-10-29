package io.reflectoring.coderadar.analyzer.levelizedStructureMap.domain;

import io.reflectoring.coderadar.analyzer.levelizedStructureMap.NoFileContentException;
import io.reflectoring.coderadar.analyzer.levelizedStructureMap.analyzers.JavaAnalyzer;
import io.reflectoring.coderadar.dependencyMap.domain.CompareNode;
import io.reflectoring.coderadar.dependencyMap.domain.CompareNodeDTO;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.vcs.UnableToGetDiffsFromCommitsException;
import io.reflectoring.coderadar.vcs.UnableToWalkCommitTreeException;
import io.reflectoring.coderadar.vcs.domain.DiffEntry;
import io.reflectoring.coderadar.vcs.port.driven.GetDiffEntriesForCommitsPort;
import io.reflectoring.coderadar.vcs.port.driven.WalkCommitTreePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DependencyCompareTree {

    private CompareNode root;
    private String repository;
    private String commitName;
    private String commitName2;

    private final JavaAnalyzer javaAnalyzer;
    private final WalkCommitTreePort walkCommitTreePort;
    private final GetDiffEntriesForCommitsPort getDiffEntriesForCommitsPort;

    private final Logger logger = LoggerFactory.getLogger(DependencyCompareTree.class);

    @Autowired
    public DependencyCompareTree(JavaAnalyzer javaAnalyzer, WalkCommitTreePort walkCommitTreePort, GetDiffEntriesForCommitsPort getDiffEntriesForCommitsPort) {
        this.javaAnalyzer = javaAnalyzer;
        this.walkCommitTreePort = walkCommitTreePort;
        this.getDiffEntriesForCommitsPort = getDiffEntriesForCommitsPort;
    }

    private void createTree() {
        try {
            walkCommitTreePort.walkCommitTree(repository, commitName, pathString -> root.createNodeByPath(pathString, null));
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
    public CompareNode getRoot(String projectRoot, String commitName, String repoName, String commitName2) {
        this.commitName2 = commitName2;
        this.repository = projectRoot;
        this.commitName = commitName;
        this.root = new CompareNode(repoName, "", "", null);
        try {
            createTree();

            List<DiffEntry> diffs = getDiffEntriesForCommitsPort.getDiffs(projectRoot, commitName, commitName2);
            for (DiffEntry entry : diffs) {
                // filter for forbidden dirs (output dirs, test dirs, ..)
                String newPath = !entry.getNewPath().equals("/dev/null") ? entry.getNewPath() : entry.getOldPath();
                if (!newPath.endsWith(".java") || newPath.contains("build") || newPath.contains("out")
                        || newPath.contains("classes") || newPath.contains("node_modules") || newPath.contains("test")) {
                    continue;
                }
                // check diff type
                if (entry.getChangeType() == ChangeType.ADD.ordinal()) {
                    // add new file with name and path to compareTree
                    root.createNodeByPath(entry.getNewPath(), ChangeType.ADD);
                } else if (entry.getChangeType() == ChangeType.MODIFY.ordinal()) {
                    // processing dependencies in dependent nodes is done when they are processed, because either those files also should have changed
                    // or they aren't changed and so they don't have this dependency any more.
                    if (!entry.getOldPath().equals(entry.getNewPath())) {
                        // if the file has been moved
                        root.createNodeByPath(entry.getNewPath(), ChangeType.ADD);
                        root.getNodeByPath(entry.getOldPath()).setChanged(ChangeType.DELETE);
                    } else {
                        root.getNodeByPath(entry.getOldPath()).setChanged(ChangeType.MODIFY);
                    }
                } else if (entry.getChangeType() == ChangeType.DELETE.ordinal()) {
                    // processing dependencies in dependent nodes is done when they are processed, because either those files also should have changed
                    // or they aren't changed and so they don't have this dependency any more.
                    // check if the node exists
                    if (root.getNodeByPath(entry.getOldPath()) != null) {
                        root.getNodeByPath(entry.getOldPath()).setChanged(ChangeType.DELETE);
                    } else {
                        // if the node does not exist, create it and set it to deleted
                        root.createNodeByPath(entry.getOldPath(), ChangeType.DELETE);
                    }
                } else if (entry.getChangeType() == ChangeType.RENAME.ordinal()) {
                    // processing dependencies in dependent nodes is done when they are processed, because either those files also should have changed
                    // or they aren't changed and so they don't have this dependency any more.
                    String oldFilename = entry.getOldPath().substring(entry.getOldPath().lastIndexOf("/") + 1);
                    String newFilename = entry.getNewPath().substring(entry.getNewPath().lastIndexOf("/") + 1);
                    // differentiate between file rename and file relocate
                    if (!oldFilename.equals(newFilename)) {
                        CompareNode tmp = root.getNodeByPath(entry.getOldPath().substring(0, entry.getOldPath().lastIndexOf("/")));
                        tmp.getChildByName(oldFilename).setFilename(newFilename);
                    } else {
                        root.createNodeByPath(entry.getNewPath(), ChangeType.ADD);
                        root.getNodeByPath(entry.getOldPath()).setChanged(ChangeType.DELETE);
                    }
                }
            }

            // traverse to set packageName and dependencies from fileContent
            this.root.traversePost(node -> {
                // set packageNames
                if (!node.hasChildren()) {
                    // set this nodes and parent nodes packageName
                    // set also parent.packageName to prevent trouble with .java ending
                    String parentPackage = "";
                    try {
                        if (ChangeType.ADD.equals(node.getChanged()) || ChangeType.MODIFY.equals(node.getChanged())) {
                            parentPackage = javaAnalyzer.getPackageName(node.getPath(), this.repository, this.commitName2);
                        } else {
                            parentPackage = javaAnalyzer.getPackageName(node.getPath(), this.repository, this.commitName);
                        }
                    } catch (NoFileContentException e) {
                        logger.error("Can't set packageName: " + e.getMessage());
                    }
                    node.getParent(root).setPackageName(parentPackage);
                    node.setPackageName(parentPackage + "." + node.getFilename());
                } else {
                    if (node.getPackageName().equals("")) {
                        String childPackage = node.getChildren().get(0).getPackageName();
                        node.setPackageName(childPackage.contains(".") ? childPackage.substring(0, childPackage.lastIndexOf(".")) : "");
                    }
                }

                // set dependencies in leave nodes from first commit,
                // dependencies in other nodes are created from the merge of new and olf leave
                if (!node.hasChildren()) {
                    try {
                        javaAnalyzer.getValidImportsFromFile(node.getPath(), projectRoot, commitName)
                                .forEach(importString -> getNodeFromImport(importString).stream()
                                        .filter(dependency -> !node.getDependencies().contains(new CompareNodeDTO(dependency))
                                                && !dependency.getFilename().equals(node.getFilename()))
                                        .map(CompareNodeDTO::new)
                                        .forEach(node::addToDependencies)
                                );
                    } catch (NoFileContentException e) {
                        logger.error("Can't set dependencies: " + e.getMessage());
                    }
                }

                // read dependencies from second commit and merge them
                if (node.hasChildren()) {
                    node.getChildren().forEach(child -> node.addToDependencies(child.getDependencies()));
                } else {
                    // analyze file second commit
                    // nodes contains new dependencies, node.dependencies contains old dependencies
                    List<CompareNode> nodes = new ArrayList<>();
                    try {
                        javaAnalyzer.getValidImportsFromFile(node.getPath(), projectRoot, commitName2)
                                .stream().map(this::getNodeFromImport).forEach(nodes::addAll);
                    } catch (NoFileContentException e) {
                        logger.error("Can't set dependencies: " + e.getMessage());
                    }

                    // migrate list to CompareNodeDTO for comparison
                    List<CompareNodeDTO> foundInSecondCommit = nodes.stream()
                            .map(currentNode -> new CompareNodeDTO(currentNode.getPath(), null))
                            .collect(Collectors.toList());

                    List<CompareNodeDTO> merged = new ArrayList<>(node.getDependencies());
                    foundInSecondCommit.forEach(dto -> {
                        if (!dto.getPath().endsWith(node.getFilename())) {
                            if (node.getDependencies().contains(dto)) {
                                // if there are elements in the new and the old list, set their changeType to the newer one
                                node.getDependencyByPath(dto.getPath()).setChanged(dto.getChanged());
                            } else if (!merged.contains(dto)) {
                                // if there are elements in the new list but not in the old one, set them to add
                                dto.setChanged(ChangeType.ADD);
                                merged.add(dto);
                            }
                        }
                    });

                    // if there are elements in the old list but nor in the new one, set them to delete
                    node.setDependencies(merged);
                    node.getDependencies().stream().filter(dto -> !foundInSecondCommit.contains(dto)).forEach(dto -> dto.setChanged(ChangeType.DELETE));
                    for (CompareNodeDTO dto : node.getDependencies()) {
                        CompareNode tmp = root.getNodeByPath(dto.getPath());
                        if (dto.getChanged() == null && tmp.getChanged() != null) {
                            if (tmp.getChanged().equals(ChangeType.ADD)) {
                                dto.setChanged(ChangeType.ADD);
                            } else if (tmp.getChanged().equals(ChangeType.DELETE)) {
                                dto.setChanged(ChangeType.DELETE);
                            }
                        }
                    }
                }
            });

            // traverse to compare nodes and to set their level
            CompareNodeComparator nodeComparator = new CompareNodeComparator();
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
        } catch (UnableToGetDiffsFromCommitsException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CompareNode> getNodeFromImport(String importString) {
        List<CompareNode> imports = new ArrayList<>();
        root.traversePre(node -> {
            // try to find node by path(importString)
            // if not found continue traversing
            // if none found importString references dependency from outside the project
            String pathString = importString;
            if (pathString.contains("*")) {
                String localImportString = pathString.substring(0, pathString.lastIndexOf("."));
                pathString = localImportString.replace(".", "/");
                if (node.getNodeByPath(pathString) != null) {
                    CompareNode tmp = node.getNodeByPath(pathString);
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

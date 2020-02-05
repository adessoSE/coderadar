package io.reflectoring.coderadar.dependencymap.adapter;

import io.reflectoring.coderadar.dependencymap.analyzers.JavaAnalyzer;
import io.reflectoring.coderadar.dependencymap.domain.CompareNode;
import io.reflectoring.coderadar.dependencymap.domain.Node;
import io.reflectoring.coderadar.dependencymap.util.CompareNodeComparator;
import io.reflectoring.coderadar.dependencymap.domain.CompareNodeDTO;
import io.reflectoring.coderadar.dependencymap.port.driven.GetCompareTreePort;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.UnableToGetDiffsFromCommitsException;
import io.reflectoring.coderadar.vcs.UnableToWalkCommitTreeException;
import io.reflectoring.coderadar.vcs.domain.DiffEntry;
import io.reflectoring.coderadar.vcs.port.driven.GetDiffEntriesForCommitsPort;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import io.reflectoring.coderadar.vcs.port.driven.WalkCommitTreePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DependencyCompareTreeAdapter implements GetCompareTreePort {

    private CompareNode root;
    private String repository;
    private String commitName;
    private String commitName2;
    private HashMap<String, byte[]> commit1Contents;
    private HashMap<String, byte[]> commit2Contents;
    private CompareNodeComparator nodeComparator;

    private final JavaAnalyzer javaAnalyzer;
    private final WalkCommitTreePort walkCommitTreePort;
    private final GetDiffEntriesForCommitsPort getDiffEntriesForCommitsPort;
    private final GetRawCommitContentPort rawCommitContentPort;

    private final Logger logger = LoggerFactory.getLogger(DependencyCompareTreeAdapter.class);

    @Autowired
    public DependencyCompareTreeAdapter(JavaAnalyzer javaAnalyzer, WalkCommitTreePort walkCommitTreePort, GetDiffEntriesForCommitsPort getDiffEntriesForCommitsPort, GetRawCommitContentPort rawCommitContentPort) {
        this.javaAnalyzer = javaAnalyzer;
        this.walkCommitTreePort = walkCommitTreePort;
        this.getDiffEntriesForCommitsPort = getDiffEntriesForCommitsPort;
        this.rawCommitContentPort = rawCommitContentPort;
        nodeComparator = new CompareNodeComparator();
    }

    private void createTree() {
        try {
            List<String> paths = new ArrayList<>();
            walkCommitTreePort.walkCommitTree(repository, commitName, pathString -> {
                root.createNodeByPath(pathString, ChangeType.UNCHANGED);
                paths.add(pathString);
            });
            commit1Contents = rawCommitContentPort.getCommitContentBulk(repository, paths, commitName);
            List<String> paths2 = new ArrayList<>();
            walkCommitTreePort.walkCommitTree(repository, commitName2, paths2::add);
            commit2Contents = rawCommitContentPort.getCommitContentBulk(repository, paths2, commitName2);
        } catch (UnableToWalkCommitTreeException | UnableToGetCommitContentException e) {
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
    @Override
    public CompareNode getRoot(String projectRoot, String commitName, String repoName, String commitName2) {
        this.commitName2 = commitName2;
        this.repository = projectRoot;
        this.commitName = commitName;
        this.root = new CompareNode(repoName, "", "", ChangeType.UNCHANGED);
        try {
            createTree();
            handleDiffs(getDiffEntriesForCommitsPort.getDiffs(projectRoot, commitName, commitName2));
            // traverse to set packageName and dependencies from fileContent
            this.root.traversePost(this::setPackage);

            commit1Contents.forEach((key, value) -> javaAnalyzer.getValidImports(new String(value))
                    .forEach(importString -> getNodeFromImport(importString)
                            .forEach(dto -> root.getNodeByPath(key).addToDependencies(dto))));

            this.root.traversePost(this::setSecondCommitDependencies);

            // traverse to compare nodes and to set their level
            this.root.traversePost(node -> {
                if (node.hasChildren()) {
                    int level = 0;
                    for (int i = 0; i < node.getChildren().size(); i++) {
                        // for every child in the current layer check
                        for (int j = 0; j < node.getChildren().size(); j++) {
                            if (i == j) continue;
                            // if any child before this has a dependency on this
                            // or any child before has more dependencies on this than this has on any child before
                            //   raise layer, break
                            if (node.getChildren().get(j).hasDependencyOn(node.getChildren().get(i))
                                    && !node.getChildren().get(i).hasDependencyOn(node.getChildren().get(j))) {
                                level++;
                            } else if (node.getChildren().get(j).countDependenciesOn(node.getChildren().get(i))
                                    > node.getChildren().get(i).countDependenciesOn(node.getChildren().get(j))) {
                                level++;
                            }
                        }
                        node.getChildren().get(i).setLevel(level);
                        level = 0;
                    }
                    node.getChildren().sort(Comparator.comparingInt(CompareNode::getLevel));
                }
            });
            return root;
        } catch (UnableToGetDiffsFromCommitsException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void handleDiffs(List<DiffEntry> diffs) {
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
    }

    private void setPackage(CompareNode node) {
        if (!node.hasChildren()) {
            // set this nodes and parent nodes packageName, also set parent.packageName to prevent trouble with .java ending
            String parentPackage;
            if (ChangeType.ADD.equals(node.getChanged()) || ChangeType.MODIFY.equals(node.getChanged())) {
                parentPackage = javaAnalyzer.getPackageName(commit2Contents.get(node.getPath()));
            } else {
                parentPackage = javaAnalyzer.getPackageName(commit1Contents.get(node.getPath()));
            }
            CompareNode parent = node.getParent(root);
            if (parent != null) {
                node.getParent(root).setPackageName(parentPackage);
            }
            node.setPackageName(parentPackage + "." + node.getFilename());
        } else {
            if (node.getPackageName().equals("")) {
                String childPackage = node.getChildren().get(0).getPackageName();
                node.setPackageName(childPackage.contains(".") ? childPackage.substring(0, childPackage.lastIndexOf(".")) : "");
            }
        }
    }

    private void setSecondCommitDependencies(CompareNode node) {
        if (node.hasChildren()) {
            node.getChildren().stream().map(CompareNode::getDependencies).forEach(node::addToDependencies);
        } else {
            // analyze file second commit
            List<CompareNode> nodes = new ArrayList<>();

            // the file has to be in the second commit and its fileContent can't be empty
            if (commit2Contents.containsKey(node.getPath()) && commit2Contents.get(node.getPath()) != null) {
                // for all valid imports of a file get all dependent CompareNode-objects and add them to nodes
                javaAnalyzer.getValidImports(new String(commit2Contents.get(node.getPath()))).stream().map(this::getNodeFromImport)
                        .forEach(importDependencies -> importDependencies.stream().filter(importDependency
                                -> !nodes.contains(importDependency) && !importDependency.getFilename().equals(node.getFilename()))
                                .forEach(nodes::add));

                // migrate nodes to CompareNodeDTO list for comparison
                List<CompareNodeDTO> foundInSecondCommit = nodes.stream().map(currentNode -> {
                            if (ChangeType.ADD.equals(currentNode.getChanged()) || ChangeType.DELETE.equals(currentNode.getChanged())) {
                                return new CompareNodeDTO(currentNode);
                            } else {
                                return new CompareNodeDTO(currentNode.getPath(), ChangeType.UNCHANGED);
                            }
                        }).collect(Collectors.toList());

                List<CompareNodeDTO> merged = new ArrayList<>(node.getDependencies());

                // for every CompareNode found in second commit which is not the current CompareNode
                foundInSecondCommit.stream().filter(compareNodeDTO -> !compareNodeDTO.getPath().endsWith(node.getFilename()))
                        .forEach(compareNodeDTO -> {
                            if (node.getDependencies().contains(compareNodeDTO)) {
                                // if there are elements in the new and the old list, set their changeType to the new changeType
                                // because it could be changed in the newer commit or left unchanged
                                CompareNodeDTO dependency = node.getDependencyByPath(compareNodeDTO.getPath());
                                if (dependency != null) {
                                    dependency.setChanged(compareNodeDTO.getChanged());
                                }
                            } else if (!merged.contains(compareNodeDTO)) {
                                // if there are elements in the new list but not in the old one, set them to add
                                compareNodeDTO.setChanged(ChangeType.ADD);
                                merged.add(compareNodeDTO);
                            }
                        });

                // if there are elements in the old list but not in the new one, set them to delete
                node.setDependencies(merged);
                node.getDependencies().forEach(dto -> {
                    if (!foundInSecondCommit.contains(dto)) {
                        dto.setChanged(ChangeType.DELETE);
                    } else {
                        CompareNode tmp = root.getNodeByPath(dto.getPath());
                        if (dto.getChanged() == null && tmp.getChanged() != null) {
                            if (tmp.getChanged().equals(ChangeType.ADD)) {
                                dto.setChanged(ChangeType.ADD);
                            } else if (tmp.getChanged().equals(ChangeType.DELETE)) {
                                dto.setChanged(ChangeType.DELETE);
                            }
                        }
                    }
                });
            } else {
                node.getDependencies().forEach(dependency -> dependency.setChanged(ChangeType.DELETE));
            }

        }
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

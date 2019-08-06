package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DependencyTree {
    private Repository repository;
    private ObjectId commitName;
    private RegexPatternCache cache;

    public static DependencyTree getTree() {
        return new DependencyTree();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *                      NodeTree                           *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Create a dependency tree from a given commit in a git repo beginning with a root Node-object.
     *
     * @param commitName commit to analyze.
     * @param repository repo to analyze.
     * @param baseRoot root Node-object.
     * @return root Node-object with its children.
     */
    public Node getDependencyTree(String commitName, Repository repository, Node baseRoot) {
        cache = new RegexPatternCache();
        this.commitName = ObjectId.fromString(commitName);
        this.repository = repository;

        JavaDependencyAnalyzer javaDependencyAnalyzer = new JavaDependencyAnalyzer();
        createTreeRoot(baseRoot);
        javaDependencyAnalyzer.setDependencies(baseRoot, baseRoot, repository, ObjectId.fromString(commitName));
        baseRoot.setDependencies(new LinkedList<>());
        sortTree(baseRoot);
        setLevel(baseRoot);
        return baseRoot;
    }

    /**
     * Create the dependency tree based on git files.
     *
     * @param root Node-object which acts as root node containing all other nodes.
     */
    private void createTreeRoot(Node root) {
        RevWalk walk = new RevWalk(repository);
        try {
            RevCommit commit = walk.parseCommit(commitName);
            RevTree tree = commit.getTree();

            TreeWalk treeWalk = new TreeWalk(repository);
            treeWalk.addTree(tree);
            treeWalk.setRecursive(false);

            // create git walk though the tree with depth = 1
            while (treeWalk.next()) {
                // filter out 'forbidden' directories like output directories or node_modules
                Matcher forbiddenDirs = cache.getPattern("(^\\.|build|out|classes|node_modules|test)").matcher(treeWalk.getNameString());
                if (!treeWalk.isSubtree() && !treeWalk.getPathString().endsWith(".java") || forbiddenDirs.find()) {
                    continue;
                }
                // check if file is directory
                if (treeWalk.isSubtree()) {
                    // get children depending on the current path
                    List<Node> children = createTree(tree, treeWalk.getPathString());

                    String packageName = "";
                    /*if (treeWalk.getPathString().contains(basepackage)) {
                        packageName = treeWalk.getPathString().substring(treeWalk.getPathString().indexOf(basepackage)).replace("/", ".");
                    }*/

                    // if children are not empty, create a node containing these children, else ignore this subtree
                    if (!children.isEmpty()) {
                        root.getChildren().add(new Node(children, treeWalk.getPathString(), treeWalk.getNameString(), packageName));
                    }
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Reads the actual children from treeWalk.
     *
     * @param pathString current path.
     * @return Children found for the current path.
     * @throws NullPointerException if the path can't be found.
     */
    private List<Node> createTree(RevTree tree, String pathString) throws NullPointerException {
        List<Node> children = new ArrayList<>();
        try {
            // create treeWalk for subtree
            TreeWalk treeWalk = TreeWalk.forPath(repository, pathString, tree);
            treeWalk.setRecursive(false);
            treeWalk.enterSubtree();
            while (treeWalk.next()) {
                // filter out 'forbidden' directories like output directories or node_modules
                Matcher forbiddenDirs = cache.getPattern("(^\\.|build|test|out|classes|node_modules)").matcher(treeWalk.getNameString());
                if (!treeWalk.isSubtree() && !treeWalk.getPathString().endsWith(".java") || forbiddenDirs.find()) {
                    continue;
                }

                String packageName = "";
                /*if (treeWalk.getPathString().contains("java/")) {
                    packageName = treeWalk.getPathString().substring(treeWalk.getPathString().indexOf("java/") + 5).replace("/", ".");
                }*/

                // if the current part is a subtree
                if (treeWalk.isSubtree()) {
                    // get children depending on the current path
                    List<Node> grandchildren = createTree(tree, treeWalk.getPathString());
                    // skip src node if there is one
                    if (!grandchildren.isEmpty()) {
                        // if children are not empty, create a node containing these children, else ignore this subtree
                        children.add(new Node(grandchildren, treeWalk.getPathString(), treeWalk.getNameString(), packageName));
                    }
                } else {
                    // else it is just a file so a node is created and added to current roots children
                    children.add(new Node(Collections.emptyList(), treeWalk.getPathString(), treeWalk.getNameString(), packageName));
                }
            }
            return children;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Sort the children of a given Node-object and their children recursively:
     * if o1 has a dependency on o2 and o2 does not have an dependency on o1
     *   o1 is before o2
     * else if o2 has a dependency on o1 and o1 does not have an dependency on o2
     *   o2 is before o1
     * else if o1 has more dependencies on o2 than o2 on o1
     *   o1 is before o2
     * else if o2 has more dependencies on o1 than o1 on o2
     *   o2 is before o1
     * else if o1 is a directory and o2 is not
     *   o1 is before o2
     * else if o2 is a directory and o1 is not
     *   o2 is before o1
     * else compare o1 and o2 lexically
     *
     * @param node Node-object which's children are to sort.
     */
    private void sortTree(Node node) {
        if (node.hasChildren()) {
            NodeComparator nodeComparator = new NodeComparator();
            for (Node child : node.getChildren()) {
                sortTree(child);
            }
            node.getChildren().sort(nodeComparator);
        }
    }

    /**
     * Set the display level of a given Node-object's children and their children recursively.
     *
     * @param node Node-object which's level is to be set.
     */
    private void setLevel(Node node) {
        int level = 0;
        for (int i = 0; i < node.getChildren().size(); i++) {
            // for every child in the current layer check
            for (int j = 0; j < i; j++) {
                if (node.getChildren().get(j).getLevel() == level) {
                    // if any child before this has a dependency on this
                    // or any child before has more dependencies on this than this has on any child before
                    //   raise layer, break
                    if (node.getChildren().get(j).hasDependencyOn(node.getChildren().get(i)) && !node.getChildren().get(i).hasDependencyOn(node.getChildren().get(j))) {
                        level++;
                        break;
                    } else if (node.getChildren().get(j).countDependenciesOn(node.getChildren().get(i)) > node.getChildren().get(i).countDependenciesOn(node.getChildren().get(j))) {
                        level++;
                        break;
                    }
                }
            }
            node.getChildren().get(i).setLevel(level);
            setLevel(node.getChildren().get(i));
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *                      CompareTree                        *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Add the diffs of a second commit to a dependency tree based on a first commit for a given repo
     *
     * @param commitName first commit to base the dependency tree on.
     * @param repository repo to analyze.
     * @param baseRoot root Node-object.
     * @param secondCommit second commit to create the diffs.
     * @return root Node-object with its children.
     */
    public CompareNode getCompareTree(String commitName, Repository repository, Node baseRoot, String secondCommit) {
        this.repository = repository;
        this.commitName = ObjectId.fromString(commitName);
        cache = new RegexPatternCache();
        CompareNode compareNode = createMergeTree(baseRoot);

        JavaDependencyAnalyzer javaDependencyAnalyzer = new JavaDependencyAnalyzer();

        addToMergeTree(compareNode, secondCommit);

        try {
            RevCommit baseCommit = repository.parseCommit(this.commitName);
            RevCommit alteredCommit = repository.parseCommit(ObjectId.fromString(secondCommit));

            javaDependencyAnalyzer.setDependenciesForCompareNode(compareNode, ObjectId.fromString(secondCommit), getDiffs(baseCommit, alteredCommit), repository);
            sortCompareTree(compareNode);
            setCompareLayer(compareNode);
            return compareNode;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Cast a dependency tree with Node-objects to a dependency tree with CompareNode-objects.
     *
     * @param baseVersion dependency tree with Node-objects to start with.
     * @return casted dependency tree with CompareNode-objects.
     */
    private CompareNode createMergeTree(Node baseVersion) {
        CompareNode compareNode = new CompareNode(new ArrayList<>(), baseVersion.getPath(), baseVersion.getFilename(), baseVersion.getPackageName(), null);
        for (Node child : baseVersion.getChildren()) {
            compareNode.getChildren().add(createMergeTree(child));
        }
        for (Node dependency : baseVersion.getDependencies()) {
            compareNode.getDependencies().add(new CompareNode(new ArrayList<>(), dependency.getPath(), dependency.getFilename(), dependency.getPackageName(), null));
        }
        return compareNode;
    }

    /**
     * Add the diffs of a second commit to a given dependency tree with CompareNode-objects.
     *
     * @param compareNode given dependency tree with CompareNode-objects.
     * @param secondCommit second commit for creating diffs.
     */
    private void addToMergeTree(CompareNode compareNode, String secondCommit) {
        try {
            RevCommit baseCommit = repository.parseCommit(commitName);
            RevCommit alteredCommit = repository.parseCommit(ObjectId.fromString(secondCommit));

            List<DiffEntry> entries = getDiffs(baseCommit, alteredCommit);
            Pattern pattern = cache.getPattern("(build|out|classes|node_modules|test)");

            for (DiffEntry entry : entries) {
                // filter for forbidden dirs (output dirs, test dirs, ..)
                Matcher forbiddenDirs = pattern.matcher(!entry.getNewPath().equals("/dev/null") ? entry.getNewPath() : entry.getOldPath());
                if (!(!entry.getNewPath().equals("/dev/null") ? entry.getNewPath() : entry.getOldPath()).endsWith(".java") || forbiddenDirs.find()) {
                    continue;
                }
                // check diff type
                if (entry.getChangeType().equals(DiffEntry.ChangeType.ADD)) {
                    // add new file with name and path to compareTree
                    compareNode.createNodeByPath(entry.getNewPath(), DiffEntry.ChangeType.ADD);
                } else if (entry.getChangeType().equals(DiffEntry.ChangeType.MODIFY)) {
                    // processing dependencies in dependent nodes is done when they are processed, because either those files also should have changed
                    // or they aren't changed and so they don't have this dependency any more.
                    if (!entry.getOldPath().equals(entry.getNewPath())) {
                        // if the file has been moved
                        compareNode.createNodeByPath(entry.getNewPath(), DiffEntry.ChangeType.ADD);
                        compareNode.getNodeByPath(entry.getOldPath()).setChanged(DiffEntry.ChangeType.DELETE);
                    }
                } else if (entry.getChangeType().equals(DiffEntry.ChangeType.DELETE)) {
                    // processing dependencies in dependent nodes is done when they are processed, because either those files also should have changed
                    // or they aren't changed and so they don't have this dependency any more.
                    // check if the node exists
                    if (compareNode.getNodeByPath(entry.getOldPath()) != null) {
                        compareNode.getNodeByPath(entry.getOldPath()).setChanged(DiffEntry.ChangeType.DELETE);
                    } else {
                        // if the node does not exist, create it and set it to deleted
                        compareNode.createNodeByPath(entry.getOldPath(), DiffEntry.ChangeType.DELETE);
                    }
                } else if (entry.getChangeType().equals(DiffEntry.ChangeType.RENAME)) {
                    // processing dependencies in dependent nodes is done when they are processed, because either those files also should have changed
                    // or they aren't changed and so they don't have this dependency any more.
                    String oldFilename = entry.getOldPath().substring(entry.getOldPath().lastIndexOf("/") + 1);
                    String newFilename = entry.getNewPath().substring(entry.getNewPath().lastIndexOf("/") + 1);
                    // differentiate between file rename and file relocate
                    if (!oldFilename.equals(newFilename)) {
                        CompareNode tmp = compareNode.getNodeByPath(entry.getOldPath().substring(0, entry.getOldPath().lastIndexOf("/")));
                        tmp.getChildByName(oldFilename).setFilename(newFilename);
                    } else {
                        compareNode.createNodeByPath(entry.getNewPath(), DiffEntry.ChangeType.ADD);
                        compareNode.getNodeByPath(entry.getOldPath()).setChanged(DiffEntry.ChangeType.DELETE);
                    }
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Get a List of all diffs between two commits.
     *
     * @param commit1 first commit.
     * @param commit2 second commit.
     * @return List with diffs.
     */
    private List<DiffEntry> getDiffs(RevCommit commit1, RevCommit commit2) {
        try {
            if (commit1.getCommitTime() > commit2.getCommitTime()) {
                RevCommit tmp = commit1;
                commit1 = commit2;
                commit2 = tmp;
            }

            ObjectReader reader = repository.newObjectReader();
            CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
            ObjectId baseTree = commit1.getTree();
            oldTreeIter.reset(reader, baseTree);
            CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
            ObjectId alteredTree = commit2.getTree();
            newTreeIter.reset(reader, alteredTree);

            DiffFormatter df = new DiffFormatter(new ByteArrayOutputStream());
            df.setRepository(repository);
            return df.scan(oldTreeIter, newTreeIter);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Sort the children of a given Node object and their children recursively:
     * if o1 has a dependency on o2 and o2 does not have an dependency on o1
     *   o1 is before o2
     * else if o2 has a dependency on o1 and o1 does not have an dependency on o2
     *   o2 is before o1
     * else if o1 has more dependencies on o2 than o2 on o1
     *   o1 is before o2
     * else if o2 has more dependencies on o1 than o1 on o2
     *   o2 is before o1
     * else if o1 is a directory and o2 is not
     *   o1 is before o2
     * else if o2 is a directory and o1 is not
     *   o2 is before o1
     * else compare o1 and o2 lexically
     *
     * @param node CompareNode-object which's children are to sort.
     */
    private void sortCompareTree(CompareNode node) {
        if (node.hasChildren()) {
            CompareNodeComparator nodeComparator = new CompareNodeComparator();
            for (CompareNode child : node.getChildren()) {
                sortCompareTree(child);
            }
            node.getChildren().sort(nodeComparator);
        }
    }

    /**
     * Set the display level of a given CompareNode-object's children and their children recursively.
     *
     * @param node CompareNode-object which's level is to be set.
     */
    private void setCompareLayer(CompareNode node) {
        int layer = 0;
        for (int i = 0; i < node.getChildren().size(); i++) {
            // for every child in the current layer check
            for (int j = 0; j < i; j++) {
                if (node.getChildren().get(j).getLevel() == layer) {
                    // if any child before this has a dependency on this
                    // or any child before has more dependencies on this than this has on any child before
                    //   raise layer, break
                    if (node.getChildren().get(j).hasDependencyOn(node.getChildren().get(i)) && !node.getChildren().get(i).hasDependencyOn(node.getChildren().get(j))) {
                        layer++;
                        break;
                    } else if (node.getChildren().get(j).countDependenciesOn(node.getChildren().get(i)) > node.getChildren().get(i).countDependenciesOn(node.getChildren().get(j))) {
                        layer++;
                        break;
                    }
                }
            }
            node.getChildren().get(i).setLevel(layer);
            setCompareLayer(node.getChildren().get(i));
        }
    }
}

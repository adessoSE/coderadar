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
import org.gitective.core.BlobUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DependencyTree {

    private String basepackage;
    private String basepackage_dot;
    private Repository repository;
    private Node baseroot;
    private ObjectId commitName;
    private RegexPatternCache cache;

    public DependencyTree(String basepackage, String commitName, Repository repository) {
        this.basepackage = basepackage;
        this.basepackage_dot = basepackage.replace("/", ".");
        cache = new RegexPatternCache();
        this.commitName = ObjectId.fromString(commitName);
        this.repository = repository;
    }

    /**
     * Set all dependencies for a given Node object including fully qualified class name usages, imports and wildcard imports
     *
     * @param root Node object to set dependencies for
     * @return Node which has its dependencies set
     */
    public void setDependencies(Node root) {
        for (Node child : root.getChildren()) {
            if (child.hasChildren()) {
                setDependencies(child);
            } else {
                // get all dependencies from imports including wildcard imports
                for (String dependency : getDependenciesFromFile(child)) {
                    String dependencyString = dependency.replace(".", "/");
                    // if import is not a wildcard import add .java to it to find the file
                    if (!dependency.matches("[a-zA-Z.]*\\*")) {
                        dependencyString += ".java";
                    }
                    addDependenciesFromDependencyString(dependencyString, child);
                }
                // get all dependencies for fully qualified class usage, ignoring:
                //   import declarations
                //   package declarations
                //   single line comments
                //   multi line comments
                //   strings
                for (String qualifiedDependency : getClassQualifiedDependencies(child)) {
                    // add .java to find the file
                    String dependencyString = qualifiedDependency.replace(".", "/") + ".java";
                    addDependenciesFromDependencyString(dependencyString, child);
                }
            }
            // add all file dependencies to the current package; done for structuring purposes
            root.getDependencies().addAll(child.getDependencies());
        }
    }

    /**
     * Add the dependencies of a dependecyString to a given Node-object. Helper method for setDependencies.
     * @param dependencyString the dependencyString containing the dependencies.
     * @param child the Node-object which manages the dependencies.
     */
    private void addDependenciesFromDependencyString(String dependencyString, Node child) {
        // remove the basepackage name from dependency to find file(s) in same package and split it into lines
        String[] pathParts = dependencyString.split("/");

        // iterate through all children til the package and filename matches the dependencyString
        List<Node> foundDependencies = findPackageNameInModules(pathParts, baseroot);
        foundDependencies.stream().filter(
                wildcardDependency -> !child.getDependencies().contains(wildcardDependency)
        )
                .forEach(wildcardDependency -> child.getDependencies().add(new Node(wildcardDependency)));
    }

    /**
     * Analyze the file of a given Node object for fully qualified class name usage dependencies. helper method for setDependencies(Node node)
     *
     * @param node Node to analyze
     * @return List of package and file names the current node has dependencies on
     */
    private List<String> getClassQualifiedDependencies(Node node) {
        if (!node.hasChildren() && node.getFilename().endsWith(".java")) {
            List<String> imports = new ArrayList<>();
            String[] lines = clearFileContent(new String(BlobUtils.getRawContent(repository, commitName, node.getPath()))).split("\n");
            for (String content : lines) {
                // dependency found if string.string.... pattern is matched and
                //   if pattern is not in a single or multi line comment
                //   if pattern is not in a string
                //   if pattern is not in an import or the package name
                Matcher importMatcher = cache.getPattern("^(?!(.*\\simport|.*\\spackage|\\s*//|\\s*/\\*|\\s*\\*|.*\")).*(([A-Za-z_$][A-Za-z_$0-9]*)\\.)+([A-Za-z_$][A-Za-z_$0-9]*)(?!\\($)").matcher(content);
                if (importMatcher.lookingAt()) {
                    String dependency = importMatcher.group();
                    // if it is an import from the current project
                    if (dependency.contains(basepackage_dot)) {
                        // extract packageName.fileName from matched region
                        Matcher dependencyMatcher = Pattern.compile("([a-zA-Z]+\\.)+[a-zA-Z]+").matcher(dependency);
                        if (dependencyMatcher.find()) {
                            String foundDependency = dependencyMatcher.group();
                            if (!imports.contains(foundDependency)) {
                                imports.add(foundDependency);
                            }
                        }
                    }
                }
            }
            return imports;
        }
        return Collections.EMPTY_LIST;
    }

    // TODO in eigene Klasse packen, übergabe per String, byte[], ..; returns List<String> imports oder über Datentyp Import, getFullyQualified auch; UnitTests separat für die Klasse
    /**
     * Analyze the file of a given Node object for import and wildcard import dependencies. helper method for setDependencies(Node node)
     *
     * @param node Node to analyze
     * @return List of package and file names the current node has dependencies on
     */
    private List<String> getDependenciesFromFile(Node node) {
        if (!node.hasChildren() && node.getFilename().endsWith(".java")) {
            List<String> imports = new ArrayList<>();
            String[] lines = clearFileContent(new String(BlobUtils.getRawContent(repository, commitName, node.getPath()))).split("\n");
            for (String content : lines) {
                // found the end of the area where import statements are valid
                //   if the line does not begin with a single or multi line comment
                //   if the line does not begin with an import statement
                //   if the line is not empty or not only contains whitespaces
                //   if the line is not the package declaration
                Matcher classMatcher = cache.getPattern("^(?!(\\s*import|\\s*$|\\s*//|\\s*/\\*|\\s*\\*|\\s*package))").matcher(content);
                if (classMatcher.find()) {
                    break;
                }
                // find all regions with an import statement
                Matcher importMatcher = cache.getPattern("^(?!(\\s*//|\\s*/\\*|\\s*\\*|.*\"))import (([A-Za-z_$][A-Za-z_$0-9]*)\\.)+(([A-Za-z_$][A-Za-z_$0-9]*)|\\*);").matcher(content);
                while (importMatcher.find()) {
                    String dependency = importMatcher.group();
                    // if it is an import from the current project
                    if (dependency.contains(basepackage_dot)) {
                        // extract packageName.fileName from matched region
                        Matcher dependencyMatcher = cache.getPattern(" ([a-zA-Z]+.)*([a-zA-Z]|\\*)").matcher(dependency);
                        if (dependencyMatcher.find()) {
                            String foundDependency = dependencyMatcher.group().substring(1);
                            if (!imports.contains(foundDependency)) {
                                imports.add(foundDependency);
                            }
                        }
                    }
                }
            }
            return imports;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Remove comments from a given fileContent
     * @param fileContent fieContent to clear
     * @return cleared fileContent
     */
    private String clearFileContent(final String fileContent) {
        return fileContent.replaceAll("(\\/\\*(.|[\\r\\n])+?\\*\\/)|(\\/\\/.*[\\r\\n])", "");
    }

    /**
     * Find package structure with @packageName in module structure working without a packageName.
     *
     * @param packageName packageName to find.
     * @param root Node-object to begin search from.
     * @return Node-objects which are found in the package.
     */
    private List<Node> findPackageNameInModules(String[] packageName, Node root) {
        List<Node> nodes = new LinkedList<>();
        // if root is a module
        if (root.getPackageName().equals("")) {
            // go through every child of root
            for (Node child : root.getChildren()) {
                // if the child is a package
                if (!child.getPackageName().equals("")) {
                    // if first part of packageName fits child.filename, else skip
                    if (child.getFilename().equals(packageName[0])) {
                        // add goThroughPackageTree(packageName, child) to nodes
                        nodes.addAll(gotThroughPackageTree(packageName, child));
                    }
                } else {
                    // else if the child is a module, add recursive to nodes
                    nodes.addAll(findPackageNameInModules(packageName, child));
                }
            }
        } else {
            // else if root is package, add goThroughPackageTree(packageName, root) to nodes
            nodes.addAll(gotThroughPackageTree(packageName, root));
        }
        return nodes;
    }

    /**
     * Go through the package structure to find the the a given Node-objects.
     *
     * @param packageName packageName of Node-Object
     * @param root Node-object to search from.
     * @return Found Node-objects.
     */
    private List<Node> gotThroughPackageTree(String[] packageName, Node root) {
        Node currentNode = root;
        for (int i = 1; i < packageName.length; i++) {
            if (currentNode != null) {
                if (currentNode.hasChildren()) {
                    // if dependencyString contains a wildcard add all children as dependencies and stop here
                    if (packageName[i].equals("*")) {
                        return currentNode.getChildren();
                    } else {
                        if (currentNode.getChildByName(packageName[i]) != null) {
                            currentNode = currentNode.getChildByName(packageName[i]);
                        } else {
                            return Collections.EMPTY_LIST;
                        }
                    }
                }
            }
        }
        return Collections.singletonList(currentNode);
    }

    /**
     * Create the DependecyTree based on git files.
     *
     * @param root Node-object which acts as root node containing all other nodes.
     */
    public void createTreeRoot(Node root) {
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
                    if (treeWalk.getPathString().contains(basepackage)) {
                        packageName = treeWalk.getPathString().substring(treeWalk.getPathString().indexOf(basepackage)).replace("/", ".");
                    }

                    // if children are not empty, create a node containing these children, else ignore this subtree
                    if (!children.isEmpty()) {
                        root.getChildren().add(new Node(children, treeWalk.getPathString(), treeWalk.getNameString(), packageName));
                    }
                }
            }
            baseroot = root;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Reads the actual children from treeWalk.
     *
     * @param tree
     * @param pathString current path
     * @return Children found for the current path.
     * @throws NullPointerException if the path can't be found, for example due to skipping /src/main/java/{basepackage}
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
                if (treeWalk.getPathString().contains("java/")) {
                    packageName = treeWalk.getPathString().substring(treeWalk.getPathString().indexOf("java/") + 5).replace("/", ".");
                }

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
     * @param node Node object which's children are to sort.
     */
    public void sortTree(Node node) {
        if (node.hasChildren()) {
            NodeComparator nodeComparator = new NodeComparator();
            for (Node child : node.getChildren()) {
                sortTree(child);
            }
            node.getChildren().sort(nodeComparator);
        }
    }

    /**
     * Set the display level of a given Node object's children and their children recursively
     *
     * @param node Node object which children's display level is set
     */
    public void setLevel(Node node) {
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

    public CompareNode createMergeTree(Node baseVersion) {
        CompareNode compareNode = new CompareNode(new ArrayList<>(), baseVersion.getPath(), baseVersion.getFilename(), baseVersion.getPackageName(), null);
        for (Node child : baseVersion.getChildren()) {
            compareNode.getCompareChildren().add(createMergeTree(child));
        }
        for (Node dependency : baseVersion.getDependencies()) {
            compareNode.getCompareDependencies().add(new CompareNode(new ArrayList<>(), dependency.getPath(), dependency.getFilename(), dependency.getPackageName(), null));
        }
        return compareNode;
    }

    public void addToMergeTree(CompareNode compareNode, String secondCommit) {
        try {
            RevCommit baseCommit = repository.parseCommit(commitName);
            RevCommit alteredCommit = repository.parseCommit(ObjectId.fromString(secondCommit));

            List<DiffEntry> entries = getDiffs(baseCommit, alteredCommit);
            Pattern pattern = cache.getPattern("(build|out|classes|node_modules|src/test)");

            for (DiffEntry entry : entries) {
                // filter for forbidden dirs (output dirs, test dirs, ..)
                Matcher forbiddenDirs = pattern.matcher(!entry.getNewPath().equals("/dev/null") ? entry.getNewPath() : entry.getOldPath());
                if (!(!entry.getNewPath().equals("/dev/null") ? entry.getNewPath() : entry.getOldPath()).endsWith(".java") || forbiddenDirs.find()) {
                    continue;
                }

                // check diff type
                if (entry.getChangeType().equals(DiffEntry.ChangeType.ADD)) {
                    // add new file with name and path to compareTree
                    compareNode.createNodeByPath(entry.getNewPath(), DiffEntry.ChangeType.ADD, basepackage);
                    // set dependencies in every dependent node
//                    setDependenciesForCompareNode(compareNode, entry.getNewPath());
                } else if (entry.getChangeType().equals(DiffEntry.ChangeType.MODIFY)) {
                    // processing dependencies in dependent nodes is done when they are processed, because either those files also should have changed
                    // or they aren't changed and so they don't have this dependency any more.
                    if (entry.getOldPath().equals(entry.getNewPath())) {
                        // if there are only changes in the file analyze file for dependencies
//                        setDependenciesForCompareNode(compareNode, entry.getNewPath());
                    } else {
                        // if the file has been moved
                        compareNode.createNodeByPath(entry.getNewPath(), DiffEntry.ChangeType.ADD, basepackage);
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
                        compareNode.createNodeByPath(entry.getOldPath(), DiffEntry.ChangeType.DELETE, basepackage);
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
                        compareNode.createNodeByPath(entry.getNewPath(), DiffEntry.ChangeType.ADD, basepackage);
                        compareNode.getNodeByPath(entry.getOldPath()).setChanged(DiffEntry.ChangeType.DELETE);
                    }
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void setDependenciesForCompareNode(CompareNode node, String secondCommit) {
        try {
            RevCommit baseCommit = repository.parseCommit(commitName);
            RevCommit alteredCommit = repository.parseCommit(ObjectId.fromString(secondCommit));

            List<DiffEntry> entries = getDiffs(baseCommit, alteredCommit);
            Pattern pattern = cache.getPattern("(build|out|classes|node_modules|src/test)");


            for (DiffEntry entry : entries) {
                /*if (!entry.getChangeType().equals(DiffEntry.ChangeType.ADD) || !entry.getChangeType().equals(DiffEntry.ChangeType.MODIFY)) {
                    continue;
                }*/
                // filter for forbidden dirs (output dirs, test dirs, ..)
                String newPath = !entry.getNewPath().equals("/dev/null") ? entry.getNewPath() : entry.getOldPath();
                Matcher forbiddenDirs = pattern.matcher(newPath);
                if (!newPath.endsWith(".java") || forbiddenDirs.find()) {
                    continue;
                }

                String[] path = newPath.split("/");
                CompareNode tmp = node.getNodeByPath(newPath);
                System.out.println("call setCompareDependencies: " + node.getFilename() + ", " + newPath);
                if (tmp == null) {
                    continue;
                }

                // iterate over every part of the new path
                for (String dependency : getDependenciesFromFile(tmp)) {
                    System.out.println("dependency for " + node.getFilename() + ": " + dependency);
                    String dependencyString = dependency.replace(".", "/");
                    if (!dependency.matches("[a-zA-Z.]*\\*")) {
                        dependencyString += ".java";
                    }
                    processCompareDependenciesFromDependencyString(dependencyString, tmp);
                }
                for (String qualifiedDependency : getClassQualifiedDependencies(tmp)) {
                    String dependencyString = qualifiedDependency.replace(".", "/") + ".java";
                    processCompareDependenciesFromDependencyString(dependencyString, tmp);
                }
                if (!tmp.getCompareDependencies().isEmpty()) {
                    for (int i = path.length - 1; i >= 0; i--) {
                        // update dependencies going up the file tree
                        CompareNode dependencyTmp = node.getNodeByPath(newPath.substring(0, newPath.indexOf("/", i)));
                        List<CompareNode> dependencies = new ArrayList<>();
                        dependencyTmp.getCompareChildren().stream().map(CompareNode::getCompareDependencies).forEach(dependencies::addAll);
                        dependencyTmp.setCompareDependencies(dependencies);
                    }
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void processCompareDependenciesFromDependencyString(String dependencyString, CompareNode child) {
        String[] pathParts = dependencyString.substring(dependencyString.lastIndexOf(basepackage) + basepackage.length() + 1).split("/");

        // iterate through all children til the package and filename matches the dependencyString
        List<Node> foundDependencies = findPackageNameInModules(pathParts, baseroot);
        foundDependencies.stream().filter(wildcardDependency -> !child.getCompareDependencies().contains(wildcardDependency))
                .forEach(wildcardDependency -> child.getDependencies().add(new CompareNode(
                        new ArrayList<>(), wildcardDependency.getPath(), wildcardDependency.getFilename(), wildcardDependency.getPackageName(), DiffEntry.ChangeType.ADD)
                ));
        child.getCompareDependencies().stream().filter(dependency -> !foundDependencies.contains(dependency))
                .forEach(dependency -> dependency.setChanged(DiffEntry.ChangeType.DELETE));
    }

    public void setCompareLayer(CompareNode node) {
        int layer = 0;
        for (int i = 0; i < node.getCompareChildren().size(); i++) {
            // for every child in the current layer check
            for (int j = 0; j < i; j++) {
                if (node.getCompareChildren().get(j).getLevel() == layer) {
                    // if any child before this has a dependency on this
                    // or any child before has more dependencies on this than this has on any child before
                    //   raise layer, break
                    if (node.getCompareChildren().get(j).hasCompareDependencyOn(node.getCompareChildren().get(i)) && !node.getCompareChildren().get(i).hasCompareDependencyOn(node.getCompareChildren().get(j))) {
                        layer++;
                        break;
                    } else if (node.getCompareChildren().get(j).countCompareDependenciesOn(node.getCompareChildren().get(i)) > node.getCompareChildren().get(i).countCompareDependenciesOn(node.getCompareChildren().get(j))) {
                        layer++;
                        break;
                    }
                }
            }
            node.getCompareChildren().get(i).setLevel(layer);
            setCompareLayer(node.getCompareChildren().get(i));
        }
    }

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
}

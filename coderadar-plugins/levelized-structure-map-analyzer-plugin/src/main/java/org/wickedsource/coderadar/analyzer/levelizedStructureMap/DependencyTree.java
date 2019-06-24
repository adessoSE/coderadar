package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.TreeRevFilter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.gitective.core.BlobUtils;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import javax.validation.constraints.Null;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
                for (String qualifiedDependency : getClassQualifierDependencies(child)) {
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
        String[] pathParts = dependencyString.substring(dependencyString.lastIndexOf(basepackage) + basepackage.length() + 1).split("/");

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
    private List<String> getClassQualifierDependencies(Node node) {
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
                Matcher forbiddenDirs = cache.getPattern("(^\\.|build|out|classes|node_modules)").matcher(treeWalk.getNameString());
                if (!treeWalk.isSubtree() && !treeWalk.getPathString().endsWith(".java") || forbiddenDirs.find()) {
                    continue;
                }
                // check if file is directory
                if (treeWalk.isSubtree()) {
                    // get children depending on the current path
                    List<Node> children = getChildren(treeWalk, tree);

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
            e.printStackTrace();
        }
    }

    /**
     * Get the children for a specific directory. Directories /src/main/java/{basepackage} are skipped.
     *
     * @param treeWalk
     * @param tree
     * @return the children.
     */
    private List<Node> getChildren(TreeWalk treeWalk, RevTree tree) {
        // set the pathString to the current path
        String pathString = treeWalk.getPathString();
        // if the current filename is src
        if (treeWalk.getNameString().equals("src")) {
            // skip it and use the next directory after /src/main/java/{basepackage}
            pathString += "/main/java/" + basepackage;
        }
        List<Node> children;
        try {
            // create tree and if necessary skip packages
            children = createTree(tree, pathString);
        } catch (NullPointerException e) {
            // if the package skipped to does not exist, use the current package even if it is named src
            children = createTree(tree, treeWalk.getPathString());
        }
        return children;
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
                Matcher forbiddenDirs = cache.getPattern("(^\\.|build|out|classes|node_modules)").matcher(treeWalk.getNameString());
                if (!treeWalk.isSubtree() && !treeWalk.getPathString().endsWith(".java") || forbiddenDirs.find()) {
                    continue;
                }

                String packageName = "";
                if (treeWalk.getPathString().contains(basepackage)) {
                    packageName = treeWalk.getPathString().substring(treeWalk.getPathString().indexOf(basepackage)).replace("/", ".");
                }

                // if the current part is a subtree
                if (treeWalk.isSubtree()) {
                    // get children depending on the current path
                    List<Node> grandchildren = getChildren(treeWalk, tree);
                    // skip src node if there is one
                    if (treeWalk.getNameString().equals("src")) {
                        return grandchildren;
                    } else if (!grandchildren.isEmpty()) {
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
            e.printStackTrace();
            return Collections.emptyList();
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
     * Set the display layer of a given Node object's children and their children recursively
     *
     * @param node Node object which children's display layer is set
     */
    public void setLayer(Node node) {
        int layer = 0;
        for (int i = 0; i < node.getChildren().size(); i++) {
            // for every child in the current layer check
            for (int j = 0; j < i; j++) {
                if (node.getChildren().get(j).getLayer() == layer) {
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
            node.getChildren().get(i).setLayer(layer);
            setLayer(node.getChildren().get(i));
        }
    }
}

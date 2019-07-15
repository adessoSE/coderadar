package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.gitective.core.BlobUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaDependencyAnalyzer {

    private RegexPatternCache cache;

    public JavaDependencyAnalyzer() {
        cache = new RegexPatternCache();
    }

    /**
     * Set all dependencies for a given Node object including fully qualified class name usages, imports and wildcard imports
     *
     * @param root Node object to set dependencies for
     * @return Node which has its dependencies set
     */
    public void setDependencies(Node root, Node baseroot, Repository repository, ObjectId commitName, String basepackage_dot) {
        for (Node child : root.getChildren()) {
            if (child.hasChildren()) {
                setDependencies(child, baseroot, repository, commitName, basepackage_dot);
            } else {
                // get all dependencies from imports including wildcard imports
                for (String dependency : getDependenciesFromFile(child, repository, commitName, basepackage_dot)) {
                    String dependencyString = dependency.replace(".", "/");
                    // if import is not a wildcard import add .java to it to find the file
                    if (!dependency.matches("[a-zA-Z.]*\\*")) {
                        dependencyString += ".java";
                    }
                    addDependenciesFromDependencyString(dependencyString, child, baseroot);
                }
                // get all dependencies for fully qualified class usage, ignoring:
                //   import declarations
                //   package declarations
                //   single line comments
                //   multi line comments
                //   strings
                for (String qualifiedDependency : getClassQualifiedDependencies(child, repository, commitName, basepackage_dot)) {
                    // add .java to find the file
                    String dependencyString = qualifiedDependency.replace(".", "/") + ".java";
                    addDependenciesFromDependencyString(dependencyString, child, baseroot);
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
    private void addDependenciesFromDependencyString(String dependencyString, Node child, Node baseroot) {
        // split the dependency String into its parts
        String[] pathParts = dependencyString.split("/");

        // iterate through all children til the package and filename matches the dependencyString
        List<Node> foundDependencies = findPackageNameInModules(pathParts, baseroot);
        foundDependencies.stream().filter(
                wildcardDependency -> !child.getDependencies().contains(wildcardDependency)
        )
                .forEach(wildcardDependency -> child.getDependencies().add(new Node(wildcardDependency)));
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
     * Analyze the file of a given Node object for fully qualified class name usage dependencies. helper method for setDependencies(Node node)
     *
     * @param node Node to analyze
     * @return List of package and file names the current node has dependencies on
     */
    private List<String> getClassQualifiedDependencies(Node node, Repository repository, ObjectId commitName, String basepackage_dot) {
        if (!node.hasChildren() && node.getFilename().endsWith(".java")) {
            byte[] bytes = BlobUtils.getRawContent(repository, commitName, node.getPath());
            if (bytes == null) {
                return Collections.emptyList();
            }
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
    private List<String> getDependenciesFromFile(Node node, Repository repository, ObjectId commitName, String basepackage_dot) {
        if (!node.hasChildren() && node.getFilename().endsWith(".java")) {
            byte[] bytes = BlobUtils.getRawContent(repository, commitName, node.getPath());
            if (bytes == null) {
                return Collections.emptyList();
            }
            List<String> imports = new ArrayList<>();
            String[] lines = clearFileContent(new String(bytes)).split("\n");
            for (String content : lines) {
                // found the end of the area where import statements are valid
                //   if the line does not begin with a single or multi line comment
                //   if the line does not begin with an import statement
                //   if the line is not empty or not only contains whitespaces
                //   if the line is not the package declaration
                Matcher classMatcher = cache.getPattern("^(?!(\\s*import|\\s*$|\\s*//|\\s*/\\*|\\s*\\*|\\s*package))").matcher(content);
                if (classMatcher.find()) {
                    continue;
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
        return Collections.emptyList();
    }

    /**
     * Remove comments from a given fileContent
     * @param fileContent fieContent to clear
     * @return cleared fileContent
     */
    private String clearFileContent(final String fileContent) {
        return fileContent.replaceAll("(\\/\\*(.|[\\r\\n])+?\\*\\/)|(\\/\\/.*[\\r\\n])", "");
    }




    public void setDependenciesForCompareNode(CompareNode node, ObjectId secondCommit, List<DiffEntry> entries, Repository repository, String basepackage_dot) {
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
            if (tmp == null) {
                continue;
            }

            // iterate over every part of the new path
            for (String dependency : getDependenciesFromFile(tmp, repository, secondCommit, basepackage_dot)) {
                String dependencyString = dependency.replace(".", "/");
                if (!dependency.matches("[a-zA-Z.]*\\*")) {
                    dependencyString += ".java";
                }
                processCompareDependenciesFromDependencyString(dependencyString, tmp, node);
            }
            for (String qualifiedDependency : getClassQualifiedDependencies(tmp, repository, secondCommit, basepackage_dot)) {
                String dependencyString = qualifiedDependency.replace(".", "/") + ".java";
                processCompareDependenciesFromDependencyString(dependencyString, tmp, node);
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
    }

    private void processCompareDependenciesFromDependencyString(String dependencyString, CompareNode child, CompareNode baseroot) {
        String[] pathParts = dependencyString.split("/");

        // iterate through all children til the package and filename matches the dependencyString
        List<Node> foundDependencies = findPackageNameInModules(pathParts, baseroot);
        foundDependencies.stream().filter(wildcardDependency -> !child.getCompareDependencies().contains(wildcardDependency))
                .forEach(wildcardDependency -> child.getDependencies().add(new CompareNode(
                        new ArrayList<>(), wildcardDependency.getPath(), wildcardDependency.getFilename(), wildcardDependency.getPackageName(), DiffEntry.ChangeType.ADD)
                ));
        child.getCompareDependencies().stream().filter(dependency -> !foundDependencies.contains(dependency))
                .forEach(dependency -> dependency.setChanged(DiffEntry.ChangeType.DELETE));
    }




}

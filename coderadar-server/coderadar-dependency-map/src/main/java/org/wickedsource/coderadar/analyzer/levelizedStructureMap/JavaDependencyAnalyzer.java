package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.gitective.core.BlobUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaDependencyAnalyzer {

    private RegexPatternCache cache;

    public JavaDependencyAnalyzer() {
        cache = new RegexPatternCache();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *                      NodeTree                           *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Set all dependencies for a given Node object including fully qualified class name usages, imports and wildcard imports.
     *
     * @param root Node-object to set dependencies for.
     */
    public void setDependencies(Node root, Node baseroot, Repository repository, ObjectId commitName) {
        for (Node child : root.getChildren()) {
            if (child.hasChildren()) {
                setDependencies(child, baseroot, repository, commitName);
            } else {
                setPackage(child, baseroot, repository, commitName);
                // get all dependencies from imports including wildcard imports
                for (String dependency : getDependenciesFromFile(!child.hasChildren() && child.getFilename().endsWith(".java"), child.getPath(), repository, commitName)) {
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
                for (String qualifiedDependency : getClassQualifiedDependencies(!child.hasChildren() && child.getFilename().endsWith(".java"), child.getPath(), repository, commitName)) {
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
     * Set the packageName of a leaf Node-object by reading its package definition. This packageName is then passed to the child's parents.
     *
     * @param child Node-object to analyse.
     * @param baseroot root Node-object to be the base for the passing of the packageName.
     */
    private void setPackage(Node child, Node baseroot, Repository repository, ObjectId commitName) {
        byte[] bytes = BlobUtils.getRawContent(repository, commitName, child.getPath());
        if (bytes != null) {
            String[] lines = new String(bytes).split("\n");
            for (String content : lines) {
                // read line with package definition from file
                Matcher packageMatcher = cache.getPattern("^(\\s*)package(\\s*)(([A-Za-z_$][A-Za-z_$0-9]*)\\.)+([A-Za-z_$][A-Za-z_$0-9]*);").matcher(content);
                if (packageMatcher.find()) {
                    // get packageName in package definition
                    Matcher dependencyMatcher = cache.getPattern(" ([a-zA-Z]+.)*([a-zA-Z]|\\*)").matcher(content);
                    if (dependencyMatcher.find()) {
                        // add filename and ending to packageName
                        String packageNameString = dependencyMatcher.group().substring(1) + "." + child.getFilename();
                        String[] packageName = packageNameString.split("\\.");
                        // set the child's packageName
                        child.setPackageName(packageNameString);
                        // set all packageNames of child's parents
                        Node tmp = baseroot.getNodeByPath(child.getPath().substring(0, child.getPath().indexOf(packageName[0]) + packageName[0].length()));
                        // use packageName.length - 2 because the filename and ending are ignored
                        for (int i = 0; i < packageName.length - 2; i++) {
                            // if this node does not have a packageName already, set it
                            if ("".equals(tmp.getPackageName())) {
                                tmp.setPackageName(packageNameString.substring(0, packageNameString.indexOf(packageName[i]) + packageName[i].length()));
                            }
                            tmp = tmp.getChildByName(packageName[i + 1]);
                        }
                        // package definition found in file. skip rest of file
                        break;
                    }
                }
            }
        }
    }

    /**
     * Add the dependencies of a dependencyString to a given Node-object. Helper method for setDependencies.
     *
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
        // if node.path ends with first part of packageName
        //   goThroughPackageTree
        //   if node is found (goThroughPackageTree is not empty)
        //     return found nodes
        // for every child
        //   findPackageNameInModules
        if (root.getPath().endsWith(packageName[0])) {
            List<Node> foundNodes = gotThroughPackageTree(packageName, root);
            if (!foundNodes.isEmpty()) {
                return foundNodes;
            }
        } else {
            List<Node> nodes = new ArrayList<>();
            for (Node child : root.getChildren()) {
                findPackageNameInModules(packageName, child).forEach(node -> {
                    if (!nodes.contains(node)) {
                        nodes.add(node);
                    }
                });
            }
            return nodes;
        }
        return Collections.emptyList();
    }

    /**
     * Go through the package structure to find Node-objects by a given packageName.
     *
     * @param packageName packageName of Node-Objects
     * @param root Node-object to search from.
     * @return found Node-objects.
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
                            return Collections.emptyList();
                        }
                    }
                }
            }
        }
        return Collections.singletonList(currentNode);
    }

    /**
     * Analyze the file of a given Node object for fully qualified class name usage dependencies. Helper method for setDependencies.
     *
     * @param path Node's path to analyze
     * @return List of package and file names the current node has dependencies on
     */
    private List<String> getClassQualifiedDependencies(boolean condition, String path, Repository repository, ObjectId commitName) {
        if (condition) {
            byte[] bytes = BlobUtils.getRawContent(repository, commitName, path);
            if (bytes == null) {
                return Collections.emptyList();
            }
            List<String> imports = new ArrayList<>();
            String[] lines = clearFileContent(new String(BlobUtils.getRawContent(repository, commitName, path))).split("\n");
            for (String content : lines) {
                // dependency found if string.string.... pattern is matched and
                //   if pattern is not in a single or multi line comment
                //   if pattern is not in a string
                //   if pattern is not in an import or the package name
                Matcher importMatcher = cache.getPattern("^(?!(.*import\\s|.*package\\s|\\s*//|\\s*/\\*|\\s*\\*|.*\")).*(([A-Za-z_$][A-Za-z_$0-9]*)\\.)+([A-Za-z_$][A-Za-z_$0-9]*)(?!\\($)").matcher(content);
                if (importMatcher.lookingAt()) {
                    String dependency = importMatcher.group();
                    // extract packageName.fileName from matched region
                    Matcher dependencyMatcher = cache.getPattern("([a-zA-Z]+\\.)+[a-zA-Z]+").matcher(dependency);
                    if (dependencyMatcher.find()) {
                        String foundDependency = dependencyMatcher.group();
                        if (!imports.contains(foundDependency)) {
                            imports.add(foundDependency);
                        }
                    }
                }
            }
            return imports;
        }
        return Collections.emptyList();
    }

    /**
     * Analyze the file of a given Node object for import and wildcard import dependencies. Helper method for setDependencies.
     *
     * @param path Node's path to analyze
     * @return List of package and file names the current node has dependencies on
     */
    private List<String> getDependenciesFromFile(boolean condition, String path, Repository repository, ObjectId commitName) {
        if (condition) {
            byte[] bytes = BlobUtils.getRawContent(repository, commitName, path);
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
            return imports;
        }
        return Collections.emptyList();
    }

    /**
     * Remove comments from a given fileContent.
     *
     * @param fileContent fieContent to clear
     * @return cleared fileContent
     */
    private String clearFileContent(final String fileContent) {
        return fileContent.replaceAll("(\\/\\*(.|[\\r\\n])+?\\*\\/)|(\\/\\/.*[\\r\\n])", "");
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *                      CompareTree                        *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Set all dependencies for a given CompareNode-object including fully qualified class name usages, imports and wildcard imports.
     *
     * @param node CompareNode-object to set dependencies for
     * @param secondCommit ID of the second commit.
     * @param entries List of DiffEntry-objects containing the changes between the two commits to compare.
     */
    public void setDependenciesForCompareNode(CompareNode node, ObjectId secondCommit, List<DiffEntry> entries, Repository repository) {
        Pattern pattern = cache.getPattern("(build|out|classes|node_modules|test)");
        for (DiffEntry entry : entries) {
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
            setComparePackage(tmp, node, repository, secondCommit);
            // iterate over every part of the new path
            List<CompareNode> dependencies = new ArrayList<>();
            for (String dependency : getDependenciesFromFile(!tmp.hasChildren() && tmp.getFilename().endsWith(".java"), tmp.getPath(), repository, secondCommit)) {
                String dependencyString = dependency.replace(".", "/");
                if (!dependency.matches("[a-zA-Z.]*\\*")) {
                    dependencyString += ".java";
                }
                dependencies.addAll(findComparePackage(dependencyString, node, tmp));
            }
            for (String qualifiedDependency : getClassQualifiedDependencies(!tmp.hasChildren() && tmp.getFilename().endsWith(".java"), tmp.getPath(), repository, secondCommit)) {
                dependencies.addAll(findComparePackage(qualifiedDependency.replace(".", "/") + ".java", node, tmp));
            }
            processCompareDependencies(dependencies, tmp);
            if (!tmp.getDependencies().isEmpty()) {
                for (int i = path.length - 1; i > 0; i--) {
                    // update dependencies going up the file tree
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < i; j++) {
                        stringBuilder.append(path[j]).append("/");
                    }
                    if (stringBuilder.length() > 0) {
                        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("/"));
                    }
                    CompareNode dependencyTmp = node.getNodeByPath(stringBuilder.toString());
                    List<CompareNode> dependenciesTmp = new ArrayList<>();
                    dependencyTmp.getChildren().stream().map(CompareNode::getDependencies).forEach(dependenciesTmp::addAll);
                    dependencyTmp.setDependencies(dependenciesTmp);
                }
            }
        }
    }

    /**
     * Add a List of CompareNode-objects to the dependencies of a given CompareNode-object. Duplicates are ignored, and the  dependency's ChangeType is set.
     *
     * @param dependencies List of CompareNode-objects to add.
     * @param child CompareNode-object to which the dependencies are added.
     */
    private void processCompareDependencies(List<CompareNode> dependencies, CompareNode child) {
        // if the foundDependencies are not in the child's dependencies, add them and set their change type to
        //   delete if the child is deleted
        //   add otherwise
        dependencies.stream().filter(dependency -> !child.getDependencies().contains(dependency))
                .forEach(dependency -> child.getDependencies().add(new CompareNode(
                        new ArrayList<>(), dependency.getPath(), dependency.getFilename(), dependency.getPackageName(),
                        (DiffEntry.ChangeType.DELETE.equals(child.getChanged()) || DiffEntry.ChangeType.DELETE.equals(dependency.getChanged()) ? DiffEntry.ChangeType.DELETE : DiffEntry.ChangeType.ADD))
                ));
    }

    /**
     * Find package structure with @packageName in module structure working without a packageName.
     *
     * @param packageName packageName to find.
     * @param root CompareNode-object to begin search from.
     * @return CompareNode-objects which are found in the package.
     */
    private List<CompareNode> findPackageNameInCompareModules(String[] packageName, CompareNode root) {
        List<CompareNode> nodes = new ArrayList<>();
        // if root is a module
        if (root.getPackageName().equals("")) {
            // go through every child of root
            for (CompareNode child : root.getChildren()) {
                // if the child is a package
                if (!child.getPackageName().equals("")) {
                    // if first part of packageName fits child.filename, else skip
                    if (child.getFilename().equals(packageName[0])) {
                        // add goThroughPackageTree(packageName, child) to nodes
                        nodes.addAll(gotThroughComparePackageTree(packageName, child));
                    }
                } else {
                    // else if the child is a module, add recursive to nodes
                    nodes.addAll(findPackageNameInCompareModules(packageName, child));
                }
            }
        } else {
            // else if root is package, add goThroughPackageTree(packageName, root) to nodes
            nodes.addAll(gotThroughComparePackageTree(packageName, root));
        }
        return nodes;
    }

    /**
     * Go through the package structure to find CompareNode-objects by a given packageName.
     *
     * @param packageName packageName of CompareNode-Object
     * @param root CompareNode-object to search from.
     * @return Found CompareNode-objects.
     */
    private List<CompareNode> gotThroughComparePackageTree(String[] packageName, CompareNode root) {
        CompareNode currentNode = root;
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
                            return Collections.emptyList();
                        }
                    }
                }
            }
        }
        return Collections.singletonList(currentNode);
    }

    /**
     * Try to find the dependencies in currentNode's package structure first. There may be more than one CompareNode-object
     * witch matches the dependency because added or deleted packages are also listed.
     *
     * @param packageName packageName to search for.
     * @param root root CompareNode-object to be the base for the search.
     * @param currentNode CompareNode-object containing the package in which to search first.
     * @return List of matching CompareNode-objects.
     */
    private List<CompareNode> findComparePackage(String packageName, CompareNode root, CompareNode currentNode) {
        // try to find depdencency in current module
        //   combine currentNode.path and packageName dependency's path to dependencyPath
        //   root.getNodeByPath(dependencyPath)
        // if it is not in the current module findPackageNameInCompareModules
        String[] packageNameParts = currentNode.getPackageName().split("\\.");
        String packageNameRefactored = String.join("/", Arrays.copyOfRange(packageNameParts, 0, packageNameParts.length - 2));

        String pathPart = currentNode.getPath().substring(0, currentNode.getPath().indexOf(packageNameRefactored.toString()));
        String localPckageName = (packageName.endsWith("/*") ? packageName.substring(0, packageName.length() - 2) : packageName);
        CompareNode dependency = root.getNodeByPath(pathPart + localPckageName);
        if (dependency != null) {
            return Collections.singletonList(dependency);
        } else {
            return findPackageNameInCompareModules(packageName.split("/"), root);
        }
    }

    /**
     * Set the packageName of a leaf CompareNode-object by reading its package definition. This packageName is then passed to the child's parents.
     *
     * @param child CompareNode-object to analyse.
     * @param baseroot root CompareNode-object to be the base for the passing of the packageName.
     */
    private void setComparePackage(CompareNode child, CompareNode baseroot, Repository repository, ObjectId commitName) {
        byte[] bytes = BlobUtils.getRawContent(repository, commitName, child.getPath());
        if (bytes != null) {
            String[] lines = new String(bytes).split("\n");
            for (String content : lines) {
                // read line with package definition from file
                Matcher packageMatcher = cache.getPattern("^(\\s*)package(\\s*)(([A-Za-z_$][A-Za-z_$0-9]*)\\.)+([A-Za-z_$][A-Za-z_$0-9]*);").matcher(content);
                if (packageMatcher.find()) {
                    // get packageName in package definition
                    Matcher dependencyMatcher = cache.getPattern(" ([a-zA-Z]+.)*([a-zA-Z]|\\*)").matcher(content);
                    if (dependencyMatcher.find()) {
                        // add filename and ending to packageName
                        String packageNameString = dependencyMatcher.group().substring(1) + "." + child.getFilename();
                        String[] packageName = packageNameString.split("\\.");
                        // set the child's packageName
                        child.setPackageName(packageNameString);
                        // set all packageNames of child's parents
                        CompareNode tmp = baseroot.getNodeByPath(child.getPath().substring(0, child.getPath().indexOf(packageName[0]) + packageName[0].length()));
                        // use packageName.length - 2 because the filename and ending are ignored
                        for (int i = 0; i < packageName.length - 2; i++) {
                            // if this node does not have a packageName already, set it
                            if ("".equals(tmp.getPackageName())) {
                                tmp.setPackageName(packageNameString.substring(0, packageNameString.indexOf(packageName[i]) + packageName[i].length()));
                            }
                            tmp = tmp.getChildByName(packageName[i + 1]);
                        }
                        // package definition found in file. skip rest of file
                        break;
                    }
                }
            }
        }
    }

}

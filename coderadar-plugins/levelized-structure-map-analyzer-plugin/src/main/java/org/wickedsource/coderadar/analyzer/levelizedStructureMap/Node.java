package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String filename;
    private String path;
    private String packageName;
    private List<Node> children;
    private List<Node> dependencies;
    private int layer;

    public Node(List<Node> children, String path, String filename, String packageName) {
        this.children = children;
        this.path = path;
        this.filename = filename;
        this.packageName = packageName;
        dependencies = new ArrayList<>();
        this.layer = -1;
    }

    /**
     * Copy constructor ignoring dependencies on other Node-objects.
     * @param node Node-object to copy.
     */
    public Node(Node node) {
        this.filename = node.getFilename();
        this.path = node.getPath();
        this.packageName = node.packageName;
        this.layer = node.getLayer();
        this.children = new ArrayList<>();
        this.dependencies = new ArrayList<>();
    }

    // Getter & Setter start

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Node> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Node> dependencies) {
        this.dependencies = dependencies;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public boolean hasDependencies() {
        return !dependencies.isEmpty();
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    // Getter & Setter end

    /**
     * Finds a child Node-object with a given filename.
     *
     * @param name filename to use.
     * @return found Node-object. Returns null if no Node-object found.
     */
    public Node getChildByName(String name) {
        if (hasChildren()) {
            for (Node child : children) {
                if (child.getFilename().equals(name)) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * Counts dependencies @this has on a given Node-object.
     *
     * @param node Node-object to check.
     * @return amount of dependencies @this has on a given Node-object.
     */
    public int countDependenciesOn(Node node) {
        // create list which contains all dependencies and sub dependencies on @node
        int counter = 0;
        // if @this is not already in that list
        for (Node dependency : dependencies) {
            // add this to the dependency list
            // check if @this has a dependency on @node
            // if @this is a file and @node is a file or @this is a folder and @node is a file
            //   dependency.equals
            // else if @this is a file and @node is a folder or @this is a folder and @node is a folder
            //   dependency.contains(@node.packageName)
            if (!this.hasChildren() && !node.hasChildren() || this.hasChildren() && !node.hasChildren()) {
                if (dependency.equals(node)) {
                    counter++;
                }
            } else if (!this.hasChildren() && node.hasChildren() || this.hasChildren() && node.hasChildren()) {
                if (dependency.getPackageName().contains(node.getPackageName().equals("") ? node.getFilename() : node.getPackageName()) ||
                        dependency.getPath().contains((node.getPackageName().equals("") ? node.getFilename() : node.getPackageName()).replaceAll("\\.", "/"))) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Check if this has a dependency on a given Node-object.
     *
     * @param node Node-object to check.
     * @return if this has a dependency on the given Node-object.
     */
    public boolean hasDependencyOn(Node node) {
        return countDependenciesOn(node) != 0;
    }

    /**
     * equals-method. Two Node-objects are equal if
     *   obj is a Node-object and
     *   this.filename equals @obj.filename and
     *   this.packageName equals @obj.packageName and
     *   this.path equals @obj.path and
     *   this.dependencies equals @obj.dependencies and
     *   this.children equals @obj.children and
     *
     * @param obj Object to check on equality.
     * @return true if both objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            return this.filename.equals(((Node) obj).getFilename()) &&
                    this.packageName.equals(((Node) obj).getPackageName()) &&
                    this.path.equals(((Node) obj).path) &&
                    this.children.equals(((Node) obj).getChildren()) &&
                    this.dependencies.equals(((Node) obj).getDependencies());
        }
        return false;
    }

    /**
     * toString method for Node. Overridden for testing purposes.
     *
     * @return Node-object.toString()
     */
    @Override
    public String toString() {
        if (!this.hasChildren()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        printDirectoryTree(this, indent, sb);
        return sb.toString();
    }

    /**
     * Helper method for toString()
     */
    private void printDirectoryTree(Node node, int indent, StringBuilder sb) {
        if (!node.hasChildren()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(node.getFilename());
        sb.append(": ");
        sb.append(node.getPackageName());
//        sb.append("; ");
//        sb.append(node.getDependencies());
        sb.append("/");
        sb.append("\n");
        for (Node child : node.getChildren()) {
            if (child.hasChildren()) {
                printDirectoryTree(child, indent + 1, sb);
            } else {
                printFile(child, indent + 1, sb);
            }
        }
    }

    /**
     * Helper method for toString()
     */
    private void printFile(Node node, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(node.getFilename());
        sb.append(": ");
        sb.append(node.getPackageName());
        sb.append("\n");
    }

    /**
     * Helper method for toString()
     */
    private String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }
}

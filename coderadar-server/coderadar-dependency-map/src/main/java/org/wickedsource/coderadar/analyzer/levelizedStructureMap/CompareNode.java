package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import org.eclipse.jgit.diff.DiffEntry;

import java.util.ArrayList;
import java.util.List;

public class CompareNode {
    private DiffEntry.ChangeType changed;

    private List<CompareNode> children;
    private List<CompareNode> dependencies;
    private String path;
    private String filename;
    private String packageName;
    private int level;

    public CompareNode() {

    }

    public CompareNode(ArrayList<CompareNode> children, String path, String filename, String packageName, DiffEntry.ChangeType changed) {
        this.changed = changed;
        this.path = path;
        this.filename = filename;
        this.packageName = packageName;
        this.children = children;
        dependencies = new ArrayList<>();
    }

    // Getter & Setter start

    public DiffEntry.ChangeType getChanged() {
        return changed;
    }

    public void setChanged(DiffEntry.ChangeType changed) {
        this.changed = changed;
    }

    public List<CompareNode> getChildren() {
        return children;
    }

    public void setChildren(List<CompareNode> children) {
        this.children = children;
    }

    public List<CompareNode> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<CompareNode> dependencies) {
        this.dependencies = dependencies;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // Getter & Setter end

    public CompareNode getChildByName(String name) {
        return children.stream().filter(child -> child.getFilename().equals(name)).findFirst().orElse(null);
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public int countCompareDependenciesOn(CompareNode node) {
        int counter = 0;
        for (CompareNode dependency : dependencies) {
            // if @node represents a file
            // else if @node represents a folder
            if (!node.hasChildren()) {
                // if the if @node is found, raise the counter
                if (dependency.equals(node)) {
                    counter++;
                }
            } else {
                // if @dependency is in the folder @node
                if (dependency.getPackageName().contains(node.getPackageName().equals("") ? node.getFilename() : node.getPackageName()) ||
                        dependency.getPath().contains((node.getPackageName().equals("") ? node.getFilename() : node.getPackageName()).replaceAll("\\.", "/"))) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public boolean hasCompareDependencyOn(CompareNode node) {
        for (CompareNode dependency : dependencies) {
            if (!this.hasChildren() && !node.hasChildren() || this.hasChildren() && !node.hasChildren()) {
                if (dependency.equals(node)) {
                    return true;
                }
            } else if (!this.hasChildren() && node.hasChildren() || this.hasChildren() && node.hasChildren()) {
                if (dependency.getPackageName().contains(node.getPackageName().equals("") ? node.getFilename() : node.getPackageName()) ||
                        dependency.getPath().contains((node.getPackageName().equals("") ? node.getFilename() : node.getPackageName()).replaceAll("\\.", "/"))) {
                    return true;
                }
            }
        }
        return false;
    }

    public CompareNode createNodeByPath(String nodePath, DiffEntry.ChangeType changed, String basepackage) {
        String[] path = nodePath.split("/");
        CompareNode tmp = this;
        // iterate over every part of the new path
        int i = 0;
        while (i < path.length) {
            String s = path[i];
            // if the Node-object already exists, iterate over it
            if (tmp.getChildByName(s) != null) {
                tmp = tmp.getChildByName(s);
            } else {
                // if it does not exist, create a new Node-object and add it to tmp's children
                String packageName = "";
                if ((tmp.getPath() + "/" + s).contains("java/")) {
                    packageName = (tmp.getPath() + "/" + s).substring(nodePath.indexOf("java/") + 5).replace("/", ".");
                }
                // create new Node
                CompareNode node = new CompareNode(new ArrayList<>(), tmp.getPath() + "/" + s, s, packageName, changed);
                tmp.getChildren().add(node);
                tmp = node;
            }
            i++;
        }
        return tmp;
    }

    public CompareNode getNodeByPath(String nodePath) {
        String[] path = nodePath.split("/");
        CompareNode tmp = this;
        // iterate over every part of the new path
        for (String s : path) {
            // if the Node-object already exists, iterate over it
            if (tmp.getChildByName(s) != null) {
                tmp = tmp.getChildByName(s);
            } else {
                return null;
            }
        }
        return tmp;
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
    private void printDirectoryTree(CompareNode node, int indent, StringBuilder sb) {
        if (!node.hasChildren()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(node.getFilename());
        sb.append(": ");
        sb.append(node.getPackageName());
        sb.append("; ");
        sb.append(node.getChanged());
        sb.append("/");
        sb.append("\n");
        for (CompareNode child : node.getChildren()) {
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
    private void printFile(CompareNode node, int indent, StringBuilder sb) {
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

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

    public List<String> countDependenciesOn(Node node) {
        // create list which contains all dependencies and sub dependencies on @node
        List<String> counter = new ArrayList<>();
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
                    counter.add(this.packageName.equals("") ? this.filename : this.packageName);
                }
            } else if (!this.hasChildren() && node.hasChildren() || this.hasChildren() && node.hasChildren()) {
                if (dependency.getPackageName().contains(node.getPackageName().equals("") ? node.getFilename() : node.getPackageName()) ||
                        dependency.getPath().contains((node.getPackageName().equals("") ? node.getFilename() : node.getPackageName()).replaceAll("\\.", "/"))) {
                    counter.add(this.packageName.equals("") ? this.filename : this.packageName);
                }
            }
        }
        return counter;
    }

    public boolean hasDependencyOn(Node node) {
        return !countDependenciesOn(node).isEmpty();
    }

    @Override
    public String toString() {
        return packageName.equals("") ? path : packageName;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void incrementLayer() {
        this.layer++;
    }

    @Override
    public boolean equals(Object obj) {
        // @this equals @obj if
        //   @obj is a Node-object and
        //   @this.filename equals @obj.filename and
        //   @this.packageName equals @obj.packageName and
        //   @this.path equals @obj.path and
        //   @this.dependencies equals @obj.dependencies and
        //   @this.children equals @obj.children and
        if (obj instanceof Node) {
            return this.filename.equals(((Node) obj).getFilename()) &&
                    this.packageName.equals(((Node) obj).getPackageName()) &&
                    this.path.equals(((Node) obj).path) &&
                    this.children.equals(((Node) obj).getChildren()) &&
                    this.dependencies.equals(((Node) obj).getDependencies());
        }
        return false;
    }
}

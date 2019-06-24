package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import java.util.List;

public class CompareNode extends Node {
    private ChangeType changed;
    private List<CompareNode> compareChildren;
    private List<CompareNode> compareDependencies;

    public CompareNode(List<Node> children, String path, String filename, String packageName, ChangeType changed) {
        super(children, path, filename, packageName);
        this.changed = changed;
    }

    // Getter & Setter start

    public ChangeType getChanged() {
        return changed;
    }

    public void setChanged(ChangeType changed) {
        this.changed = changed;
    }

    public List<CompareNode> getCompareChildren() {
        return compareChildren;
    }

    public void setCompareChildren(List<CompareNode> compareChildren) {
        this.compareChildren = compareChildren;
    }

    public List<CompareNode> getCompareDependencies() {
        return compareDependencies;
    }

    public void setCompareDependencies(List<CompareNode> compareDependencies) {
        this.compareDependencies = compareDependencies;
    }

    // Getter & Setter end

    public Node getNodeFromCompareNode() {
        return (Node) this;
    }
}

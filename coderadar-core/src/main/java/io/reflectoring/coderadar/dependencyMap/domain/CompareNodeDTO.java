package io.reflectoring.coderadar.dependencyMap.domain;

import io.reflectoring.coderadar.plugin.api.ChangeType;

public class CompareNodeDTO {

    private String path;
    private ChangeType changed;

    public CompareNodeDTO(String path, ChangeType changeType) {
        this.path = path;
        this.changed = changeType;
    }

    /**
     * Create a CompareNodeDTO-object from a given CompareNode-object.
     *
     * @param node CompareNode-object to 'copy'.
     */
    public CompareNodeDTO(CompareNode node) {
        this(node.getPath(), node.getChanged());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ChangeType getChanged() {
        return changed;
    }

    public void setChanged(ChangeType changed) {
        this.changed = changed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompareNodeDTO) {
            return ((CompareNodeDTO) obj).getPath().equals(this.getPath());
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" +
                "path='" + path + '\'' +
                ", changeType=" + changed +
                '}';
    }
}

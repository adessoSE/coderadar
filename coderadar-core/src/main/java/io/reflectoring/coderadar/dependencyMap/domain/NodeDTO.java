package io.reflectoring.coderadar.dependencymap.domain;

public class NodeDTO {

    private String path;

    public NodeDTO(String path) {
        this.path = path;
    }

    public NodeDTO() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NodeDTO) {
            return ((NodeDTO) obj).getPath().equals(this.getPath());
        }
        return false;
    }
}

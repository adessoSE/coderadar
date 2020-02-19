package io.reflectoring.coderadar.dependencymap.domain;

import lombok.Data;

@Data
public class NodeDTO {

    private String path;

    public NodeDTO(String path) {
        this.path = path;
    }

    public NodeDTO() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NodeDTO) {
            return ((NodeDTO) obj).getPath().equals(this.getPath());
        }
        return false;
    }
}

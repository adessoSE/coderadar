package io.reflectoring.coderadar.dependencymap.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeDTO {

  private String path;

  public NodeDTO(String path) {
    this.path = path;
  }

  public NodeDTO() {}

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof NodeDTO) {
      return ((NodeDTO) obj).getPath().equals(this.getPath());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + (path == null ? 0 : path.hashCode());
    return hash;
  }
}

package io.reflectoring.coderadar.dependencymap.domain;

import io.reflectoring.coderadar.plugin.api.ChangeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompareNodeDTO {

  private String path;
  private ChangeType changed;

  public CompareNodeDTO(String path, ChangeType changeType) {
    this.path = path;
    this.changed = changeType;
  }

  public CompareNodeDTO() {}

  /**
   * Create a CompareNodeDTO-object from a given CompareNode-object.
   *
   * @param node CompareNode-object to 'copy'.
   */
  public CompareNodeDTO(CompareNode node) {
    this(node.getPath(), node.getChanged());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CompareNodeDTO) {
      return ((CompareNodeDTO) obj).getPath().equals(this.getPath());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + (path == null ? 0 : path.hashCode());
    return hash;
  }

  @Override
  public String toString() {
    return "{" + "path='" + path + '\'' + ", changeType=" + changed + '}';
  }
}

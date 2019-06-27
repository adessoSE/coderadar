package io.reflectoring.coderadar.analyzer.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModuleAssociationId implements Serializable {

  private Long commitId;

  private Long fileId;

  private Long moduleId;

  @Override
  public int hashCode() {
    return 31 + commitId.hashCode() + fileId.hashCode() + moduleId.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ModuleAssociationId)) {
      return false;
    }
    ModuleAssociationId that = (ModuleAssociationId) obj;
    return this.commitId.equals(that.commitId)
        && this.fileId.equals(that.fileId)
        && this.moduleId.equals(that.moduleId);
  }
}

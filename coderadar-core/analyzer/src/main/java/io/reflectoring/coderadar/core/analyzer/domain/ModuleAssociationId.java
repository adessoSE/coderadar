package io.reflectoring.coderadar.core.analyzer.domain;

import java.io.Serializable;
import javax.persistence.Column;
import lombok.Data;

@Data
public class ModuleAssociationId implements Serializable {

  @Column(name = "commit_id")
  private Long commitId;

  @Column(name = "file_id")
  private Long fileId;

  @Column(name = "module_id")
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

  /*
  @Override
  public String toString() {
    return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
  */
}

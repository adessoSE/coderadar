package org.wickedsource.coderadar.commit.domain;

import java.io.Serializable;
import javax.persistence.Column;

public class ModuleAssociationId implements Serializable {

  @Column(name = "commit_id")
  private Long commitId;

  @Column(name = "file_id")
  private Long fileId;

  @Column(name = "module_id")
  private Long moduleId;

  public Long getCommitId() {
    return commitId;
  }

  public void setCommitId(Long commitId) {
    this.commitId = commitId;
  }

  public Long getFileId() {
    return fileId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }

  public Long getModuleId() {
    return moduleId;
  }

  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }
}

package org.wickedsource.coderadar.commit.domain;

import java.io.Serializable;
import javax.persistence.Column;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

	@Override
	public int hashCode() {
		return 31 + commitId.hashCode() + fileId.hashCode() + moduleId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ModuleAssociationId)) {
			return false;
		}
		ModuleAssociationId that = (ModuleAssociationId) obj;
		return this.commitId.equals(that.commitId)
				&& this.fileId.equals(that.fileId)
				&& this.moduleId.equals(that.moduleId);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}

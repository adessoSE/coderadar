package org.wickedsource.coderadar.commit.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
* This Entity exists merely to be able to do bulk changes via HQL. When possible the actual
* ManyToMany field in CommitToFileAssociation.modules should be used.
*/
@Entity
@Table(name = "commit_file_module")
public class ModuleAssociation {

	@EmbeddedId private ModuleAssociationId id;

	public ModuleAssociationId getId() {
		return id;
	}

	public void setId(ModuleAssociationId id) {
		this.id = id;
	}
}

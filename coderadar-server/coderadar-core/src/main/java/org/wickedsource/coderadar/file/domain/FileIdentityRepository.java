package org.wickedsource.coderadar.file.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FileIdentityRepository extends CrudRepository<FileIdentity, Long> {

	@Query(
			"delete FileIdentity i where i.id in (select a.id.file.identity.id from CommitToFileAssociation a where a.id.commit.project.id = :projectId)")
	@Modifying
	int deleteByProjectId(@Param("projectId") Long projectId);
}

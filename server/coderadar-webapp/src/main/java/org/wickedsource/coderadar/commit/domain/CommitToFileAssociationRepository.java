package org.wickedsource.coderadar.commit.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommitToFileAssociationRepository extends CrudRepository<CommitToFileAssociation, CommitToFileId> {

    @Query("delete CommitToFileAssociation a where a.id.commit.id in (select c.id from Commit c where c.project.id=:projectId)")
    @Modifying
    int deleteByProjectId(@Param("projectId") Long id);

    CommitToFileAssociation save(CommitToFileAssociation a);

}

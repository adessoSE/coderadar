package org.wickedsource.coderadar.commit.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ModuleAssociationRepository extends CrudRepository<ModuleAssociation, ModuleAssociationId> {

    @Query("delete ModuleAssociation m where m.id.moduleId in (select m2.id from Module m2 where m2.project.id = :projectId)")
    @Modifying
    int deleteByProjectId(@Param("projectId") Long id);

}

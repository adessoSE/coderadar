package org.wickedsource.coderadar.metric.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

import java.util.List;

public interface SourceFileRepository extends SourceFileRepositoryCustom, CrudRepository<SourceFile, Long> {

    @Query("select f from Commit c join c.sourceFiles a join a.id.sourceFile f where f.filepath=:filepath and c.name=:commitName")
    SourceFile findInCommit(@Param("filepath") String filepath, @Param("commitName") String commitName);

    @Query("select f from Commit c join c.sourceFiles a join a.id.sourceFile f where a.changeType in (:changeTypes) and c.name=:commitName")
    List<SourceFile> findInCommit(@Param("changeTypes") List<ChangeType> changeType, @Param("commitName") String commitName);

}
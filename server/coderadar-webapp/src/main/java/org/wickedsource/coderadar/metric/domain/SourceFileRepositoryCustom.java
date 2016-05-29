package org.wickedsource.coderadar.metric.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SourceFileRepositoryCustom {

    @Query("select f from Commit c join c.sourceFiles a join a.id.sourceFile f where c.name=:commitName and f.filepath in (:filepaths)")
    List<SourceFile> findInCommit(@Param("commitName") String commitName, @Param("filepaths") List<String> filepaths);
}

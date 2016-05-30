package org.wickedsource.coderadar.file.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepositoryCustom {

    @Query("select f from Commit c join c.files a join a.id.file f where c.name=:commitName and f.filepath in (:filepaths)")
    List<File> findInCommit(@Param("commitName") String commitName, @Param("filepaths") List<String> filepaths);
}

package org.wickedsource.coderadar.file.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

import java.util.List;

public interface FileRepository extends FileRepositoryCustom, CrudRepository<File, Long> {

    @Query("select f from Commit c join c.files a join a.id.file f where f.filepath=:filepath and c.name=:commitName")
    File findInCommit(@Param("filepath") String filepath, @Param("commitName") String commitName);

    @Query("select f from Commit c join c.files a join a.id.file f where a.changeType in (:changeTypes) and c.name=:commitName")
    List<File> findInCommit(@Param("changeTypes") List<ChangeType> changeType, @Param("commitName") String commitName);

}
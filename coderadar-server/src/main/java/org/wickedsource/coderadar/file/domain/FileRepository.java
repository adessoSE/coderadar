package org.wickedsource.coderadar.file.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

import java.util.List;

public interface FileRepository extends FileRepositoryCustom, CrudRepository<File, Long> {

  @Query(
      "select f from Commit c join c.files a join a.id.file f where f.filepath=:filepath and c.name=:commitName and c.project.id = :projectId")
  List<File> findInCommit(
      @Param("filepath") String filepath,
      @Param("commitName") String commitName,
      @Param("projectId") Long projectId);

  @Query(
      "select f from Commit c join c.files a join a.id.file f where a.changeType in (:changeTypes) and c.name=:commitName and c.project.id = :projectId")
  List<File> findInCommit(
      @Param("changeTypes") List<ChangeType> changeType,
      @Param("commitName") String commitName,
      @Param("projectId") Long projectId);

  @Query(
      "delete File f where f.id in (select a.id.file.id from CommitToFileAssociation a where a.id.commit.project.id = :projectId)")
  @Modifying
  int deleteByProjectId(@Param("projectId") Long projectId);
}

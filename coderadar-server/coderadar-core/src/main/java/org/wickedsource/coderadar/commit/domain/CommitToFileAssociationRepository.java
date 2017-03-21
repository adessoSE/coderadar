package org.wickedsource.coderadar.commit.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommitToFileAssociationRepository
    extends CrudRepository<CommitToFileAssociation, CommitToFileId> {

  @Query(
      "delete CommitToFileAssociation a where a.id.commit.id in (select c.id from Commit c where c.project.id=:projectId)")
  @Modifying
  int deleteByProjectId(@Param("projectId") Long id);

  CommitToFileAssociation save(CommitToFileAssociation a);

  /**
   * Associates the ommit with the specified ID with all files that are contained in the parent
   * commit.
   *
   * <p>This is done by performing an "insert into select ..." on the database instead of loading
   * them all into Java to optimize performance by several orders of magnitude.
   *
   * @param commitId ID of the commit to associate with all files from it's parent commit
   * @return number of files associated with the commit.
   */
  @Query(name = "CommitToFileAssociation.associateWithFilesFromParentCommit")
  @Modifying
  int associateWithFilesFromParentCommit(@Param("commitId") long commitId);
}

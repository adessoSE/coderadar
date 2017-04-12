package org.wickedsource.coderadar.graph.domain.file;

import java.util.Set;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface FileNodeRepository extends GraphRepository<FileNode> {

  FileNode findByFileId(FileId fileId);

  @Query(
      "MATCH "
          + "(addedFile:File)-[:ADDED_IN_COMMIT]->(c:Commit {name:{0}}),"
          + "(modifiedFile:File)-[:MODIFIED_IN_COMMIT]->(c:Commit {name:{0}}),"
          + "(deletedFile:File)-[:DELETED_IN_COMMIT]->(c:Commit {name:{0}}),"
          + "(renamedFile:File)-[:RENAMED_IN_COMMIT]->(c:Commit {name:{0}})"
          + "RETURN "
          + "count(addedFile) as addedFiles, "
          + "count(modifiedFile) as modifiedFiles, "
          + "count(deletedFile) as deletedFiles, "
          + "count(renamedFile) as renamedFiles")
  TouchedFilesCounts countTouchedFiles(String commitName);

  @Query(
      "MATCH "
          + "(child:Commit {name:{0}})-[:IS_CHILD_OF*]->(parent:Commit), "
          + "(parent)-[:TOUCHED]->(file:File) "
          + "WHERE NOT ((file)-[:DELETED_IN_COMMIT]->()) "
          + "RETURN file")
  Set<FileNode> findFilesFromPreviousCommits(String commitName);

  @QueryResult
  class TouchedFilesCounts {
    int addedFiles;
    int modifiedFiles;
    int deletedFiles;
    int renamedFiles;

    public int getAddedFiles() {
      return addedFiles;
    }

    public int getModifiedFiles() {
      return modifiedFiles;
    }

    public int getDeletedFiles() {
      return deletedFiles;
    }

    public int getRenamedFiles() {
      return renamedFiles;
    }
  }
}

package org.wickedsource.coderadar.graph.domain.file;

import java.util.Set;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface FileNodeRepository extends GraphRepository<FileNode> {

  @Query(
      "MATCH (c:Commit {name:{0}})"
          + "OPTIONAL MATCH (addedFile:File)-[:ADDED_IN_COMMIT]->(c)"
          + "OPTIONAL MATCH (modifiedFile:File)-[:MODIFIED_IN_COMMIT]->(c)"
          + "OPTIONAL MATCH (deletedFile:File)-[:DELETED_IN_COMMIT]->(c)"
          + "OPTIONAL MATCH (renamedFile:File)-[:RENAMED_IN_COMMIT]->(c)"
          + "RETURN "
          + "count(addedFile) as addedFiles, "
          + "count(modifiedFile) as modifiedFiles, "
          + "count(deletedFile) as deletedFiles, "
          + "count(renamedFile) as renamedFiles")
  TouchedFilesCountQueryResult countTouchedFiles(String commitName);

  @Query(
      "MATCH "
          + "(child:Commit {name:{0}})-[:IS_CHILD_OF*]->(parent:Commit), "
          + "(parent)-[:TOUCHED]->(file:File) "
          + "WHERE NOT ((file)-[:DELETED_IN_COMMIT]->()) "
          + "RETURN file")
  Set<FileNode> notDeletedInPreviousCommits(String commitName);
}

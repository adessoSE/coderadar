package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.List;

public interface FileRepository extends Neo4jRepository<FileEntity, Long> {

  /**
   * @param projectId The project id.
   * @return All of the files in a project (including those part of modules).
   */
  @Query("MATCH (p)-[:CONTAINS*]->(f:FileEntity) WHERE ID(p) = {0} RETURN f")
  @NonNull
  List<FileEntity> findAllinProject(long projectId);

  /**
   * Creates [:RENAMED_FROM] relationships between files.
   *
   * @param renameRels A list of maps, each containing two file ids. The file being renamed
   *     ("fileId1") and the file its being renamed from ("fileId2").
   */
  @Query(
      "UNWIND {0} as x "
          + "MATCH (f1) WHERE ID(f1) = x.fileId1 "
          + "MATCH (f2) WHERE ID(f2) = x.fileId2 "
          + "CREATE (f1)-[:RENAMED_FROM]->(f2)")
  void createRenameRelationships(@NonNull List<HashMap<String, Object>> renameRels);

  /**
   * @param projectId The project id.
   * @return The file with the given sequence id or null if it does not exist.
   */
  @Query(
      "MATCH (p)-[:CONTAINS*]->(f:FileEntity) WHERE ID(p) = {0} AND f.sequenceId = {1} RETURN f LIMIT 1 ")
  FileEntity getFileInProjectBySequenceId(long projectId, long sequenceId);
}

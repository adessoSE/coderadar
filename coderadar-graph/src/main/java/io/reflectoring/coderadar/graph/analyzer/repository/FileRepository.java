package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.query.domain.FileAndCommitsForTimePeriodQueryResult;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

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
          + "MATCH (f1) WHERE ID(f1) = x[0] "
          + "MATCH (f2) WHERE ID(f2) = x[1] "
          + "CREATE (f1)-[:RENAMED_FROM]->(f2)")
  void createRenameRelationships(@NonNull List<long[]> renameRels);

  /**
   * @param projectId The project id.
   * @return The file with the given sequence id or null if it does not exist.
   */
  @Query(
      "MATCH (p)-[:CONTAINS*]->(f:FileEntity) WHERE ID(p) = {0} AND f.path = {1} RETURN f LIMIT 1 ")
  FileEntity getFileInProjectByPath(long projectId, String path);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} AND "
          + "c.hash = {1} WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c "
          + "WHERE c.timestamp >= {2} WITH c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)-[:DELETED_IN]->(c) RETURN collect(f) as deletes', {commits: commits}) "
          + "YIELD value WITH commits, value.deletes as deletes "
          + "CALL apoc.cypher.run('UNWIND commits as c MATCH (c)<-[:CHANGED_IN]-(f) WHERE NOT(f IN {deletes}) "
          + "AND any(x IN includes WHERE f.path =~ x) AND none(x IN excludes WHERE f.path =~ x) "
          + "RETURN f', {commits:commits, deletes: deletes, includes: {4}, excludes: {5}}) YIELD value "
          + "WITH value.f as f, commits WITH commits, f "
          + "OPTIONAL MATCH (f)-[:RENAMED_FROM*0..]->(f2) WITH collect(f) + collect(f2) as files, commits "
          + "UNWIND files AS f "
          + "UNWIND commits as c "
          + "MATCH (c)<-[:CHANGED_IN]-(f) WITH collect(DISTINCT c) as commits, f "
          + "WHERE size(commits) >= {3} RETURN f.path AS path, commits ORDER BY f.path")
  List<FileAndCommitsForTimePeriodQueryResult> getFrequentlyChangedFiles(
      long projectId,
      String commitHash,
      long dateTime,
      int frequency,
      @NonNull List<String> includes,
      @NonNull List<String> excludes);

  /**
   * @param projectId The id of the project.
   * @param path The filepath to search for.
   * @return True if any file with the given path exists.
   */
  @Query(
      "MATCH (p)-[:CONTAINS]->(f:FileEntity) WHERE ID(p) = {0} AND f.path = {1} WITH f LIMIT 1 RETURN COUNT(f) > 0 ")
  boolean fileWithPathExists(long projectId, @NonNull String path);

  /**
   * @param path The path to search in.
   * @param projectOrModuleId The id of the project or a module.
   * @return True if any file falls under the given path, false otherwise.
   */
  @Query(
      "MATCH (p)-[:CONTAINS]->(f:FileEntity) WHERE ID(p) = {1} AND f.path STARTS WITH {0} WITH f LIMIT 1 RETURN COUNT(f) > 0 ")
  boolean fileInPathExists(@NonNull String path, long projectOrModuleId);
}

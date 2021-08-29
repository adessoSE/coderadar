package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.query.domain.ContributorsForFileQueryResult;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ContributorQueryRepository extends Neo4jRepository<ContributorEntity, Long> {

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $2 WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN]->(c) RETURN collect(f) as renames', {commits: commits}) "
          + "YIELD value WITH commits, value.renames as renames "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)-[:DELETED_IN]->(c) RETURN collect(f) as deletes', {commits: commits}) "
          + "YIELD value WITH commits, renames, value.deletes as deletes "
          + "UNWIND commits as c "
          + "MATCH (f)-[:CHANGED_IN]->(c) WHERE NOT(f IN deletes OR f IN renames) AND any(x IN $3 WHERE f.path =~ x) AND none(x IN $4 WHERE f.path =~ x) "
          + "WITH f.path as path, collect(DISTINCT c.authorEmail) AS emails "
          + "UNWIND emails as email "
          + "MATCH (co:ContributorEntity) WHERE toLower(email) IN co.emails WITH path, collect(DISTINCT co.displayName) as contributors "
          + "WHERE size(contributors) = $1 RETURN path, contributors")
  List<ContributorsForFileQueryResult> getFilesWithContributors(
      long projectId,
      int numberOfContributors,
      long commitHash,
      @NonNull List<String> includes,
      @NonNull List<String> excludes);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $1 WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c "
          + "ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)-[:DELETED_IN]->(c) "
          + "RETURN collect(f) as deletes', {commits: commits}) YIELD value WITH commits, value.deletes as deletes "
          + "MATCH (p)-[:CONTAINS*1..]->(f)-[:RENAMED_FROM*0..]->(f2) WHERE ID(p) = $0 AND NOT f IN deletes "
          + "AND f.path = $2 WITH collect(f) + collect(f2) as files, commits "
          + "UNWIND commits as c "
          + "MATCH (c)<-[:CHANGED_IN]-(f) WHERE f IN files WITH DISTINCT c.authorEmail as email "
          + "MATCH (co:ContributorEntity)-[:WORKS_ON]->(p) WHERE ID(p) = $0 AND toLower(email) IN co.emails RETURN co ORDER BY co.displayName")
  List<ContributorEntity> findAllByProjectIdAndFilepathInCommit(
      long projectId, long commitHash, String filepath);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $1 WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c "
          + "ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)-[:DELETED_IN]->(c) "
          + "RETURN collect(f) as deletes', {commits: commits}) YIELD value WITH commits, value.deletes as deletes "
          + "MATCH (p)-[:CONTAINS*1..]->(f)-[:RENAMED_FROM*0..]->(f2) WHERE ID(p) = $0 AND NOT f IN deletes "
          + "AND f.path STARTS WITH $2 WITH collect(f) + collect(f2) as files, commits "
          + "UNWIND commits as c "
          + "MATCH (c)<-[:CHANGED_IN]-(f) WHERE f IN files WITH DISTINCT c.authorEmail as email "
          + "MATCH (co:ContributorEntity)-[:WORKS_ON]->(p) WHERE ID(p) = $0 AND toLower(email) IN co.emails RETURN co ORDER BY co.displayName")
  List<ContributorEntity> findAllByProjectIdAndDirectoryInCommit(
      long projectId, long commitHash, String directory);
}

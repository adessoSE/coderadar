package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.query.domain.ContributorsForFileQueryResult;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

public interface ContributorQueryRepository extends Neo4jRepository<ContributorEntity, Long> {

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} AND c.name = {2} WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN]->(c) RETURN collect(f) as renames', {commits: commits}) "
          + "YIELD value WITH commits, value.renames as renames "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)-[:CHANGED_IN {changeType: \"DELETE\"}]->(c) RETURN collect(f) as deletes', {commits: commits}) "
          + "YIELD value WITH commits, renames, value.deletes as deletes "
          + "UNWIND commits as c "
          + "MATCH (f)-[:CHANGED_IN]->(c) WHERE NOT(f IN deletes OR f IN renames) AND any(x IN {3} WHERE f.path =~ x) AND none(x IN {4} WHERE f.path =~ x) "
          + "WITH f.path as path, collect(DISTINCT c.authorEmail) AS emails "
          + "UNWIND emails as email "
          + "MATCH (co:ContributorEntity) WHERE toLower(email) IN co.emails WITH path, collect(DISTINCT co.displayName) as contributors "
          + "WHERE size(contributors) = {1} RETURN path, contributors")
  List<ContributorsForFileQueryResult> getCriticalFiles(
      long projectId,
      int numberOfContributors,
      String commitHash,
      @NonNull List<String> includes,
      @NonNull List<String> excludes);
}

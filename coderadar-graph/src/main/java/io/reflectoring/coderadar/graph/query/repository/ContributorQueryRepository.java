package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.query.domain.ContributorsForFileQueryResult;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

public interface ContributorQueryRepository extends Neo4jRepository<ContributorEntity, Long> {
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(co)<-[:CHANGED_IN {changeType: \"DELETE\"}]-(f) WHERE ID(p) = {0} WITH collect(DISTINCT f) AS deletes "
          + "MATCH (c)-[:WORKS_ON]->(p)-[:CONTAINS*]->(f)-[:CHANGED_IN]->(co) WHERE ID(p) = {0} AND any(x IN {2} WHERE f.path =~ x) "
          + "AND none(x IN {3} WHERE f.path =~ x) "
          + "AND NOT f IN deletes AND toLower(co.authorEmail) IN c.emails AND NOT ()-[:RENAMED_FROM]->(f) "
          + "WITH f.path as path, collect(DISTINCT c.displayName) AS contributors "
          + "WHERE size(contributors) = {1} RETURN path, contributors")
  List<ContributorsForFileQueryResult> getCriticalFiles(
      @NonNull Long projectId,
      int numberOfContributors,
      @NonNull List<String> includes,
      @NonNull List<String> excludes);
}

package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.FindingEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface FindingRepository extends Neo4jRepository<FindingEntity, Long> {
  @Query(
      "MATCH (p)-[:CONTAINS*]->()-[:MEASURED_BY]->()-[:LOCATED_IN]->(fi) "
          + "WHERE ID(p) = {0} RETURN fi")
  List<FindingEntity> findByProjectId(@NonNull Long projectId);
}

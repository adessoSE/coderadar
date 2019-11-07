package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.FindingEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FindingRepository extends Neo4jRepository<FindingEntity, Long> {
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(:FileEntity)-[:MEASURED_BY]->()-[:LOCATED_IN]->(fi:FindingEntity) "
          + "WHERE ID(p) = {0} RETURN fi")
  List<FindingEntity> findByProjectId(Long projectId);
}

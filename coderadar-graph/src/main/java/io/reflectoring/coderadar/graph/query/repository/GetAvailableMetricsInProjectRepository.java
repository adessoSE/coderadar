package io.reflectoring.coderadar.graph.query.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetAvailableMetricsInProjectRepository extends Neo4jRepository {

  @Query("MATCH (p1:ProjectEntity)-[:CONTAINS*]-(f1:FileEntity)-[:MEASURED_BY]->(n:MetricValueEntity) WHERE ID(p1) = {0} RETURN DISTINCT n.name")
  List<String> getAvailableMetricsInProject(long projectId);
}

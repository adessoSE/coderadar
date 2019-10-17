package io.reflectoring.coderadar.graph.query.repository;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAvailableMetricsInProjectRepository extends Neo4jRepository {

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS]->(:CommitEntity)<-[:VALID_FOR]-(mv:MetricValueEntity) WHERE ID(p) = {0} RETURN DISTINCT mv.name")
  List<String> getAvailableMetricsInProject(long projectId);
}

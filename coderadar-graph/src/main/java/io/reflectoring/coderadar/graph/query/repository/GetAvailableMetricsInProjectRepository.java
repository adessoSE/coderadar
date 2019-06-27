package io.reflectoring.coderadar.graph.query.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetAvailableMetricsInProjectRepository extends Neo4jRepository {
  // TODO: Probably doesn't work because metricValues is a relationship and not an attribute
  @Query(
      "MATCH (p:Project)-[:HAS]->(c:Commit)-[:HAS_CHANGED]->(a:FileToCommitRelationship) WHERE ID(p) = {0} RETURN a.file.metricValues")
  List<String> getAvailableMetricsInProject(long projectId);
}

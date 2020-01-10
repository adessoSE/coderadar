package io.reflectoring.coderadar.graph.query.repository;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAuthorsRepository extends Neo4jRepository {
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} RETURN DISTINCT c.author")
  List<String> getAuthors(@NonNull Long projectId);
}

package io.reflectoring.coderadar.graph.contributor.repository;

import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends Neo4jRepository<ContributorEntity, Long> {
  @Query("MATCH (c) WHERE ID(c) = {0} RETURN c")
  Optional<ContributorEntity> findById(long id);

  @Query("MATCH (c) WHERE ID(c) IN {0} RETURN c")
  List<ContributorEntity> findAllByIds(List<Long> ids);

  @Query("MATCH (co:ContributorEntity) WHERE SIZE((co)-[:WORKS_ON]->()) = 0 DELETE co")
  void deleteContributorsWithoutProjects();

  @Query("MATCH (c)-[:WORKS_ON]->(p) WHERE ID(p) = {0} RETURN c ORDER BY c.displayName")
  List<ContributorEntity> findAllByProjectId(long projectId);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} AND c.name = {1} WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c MATCH (c)<-[:CHANGED_IN]-(f) WHERE f.path = {path} RETURN f', {commits:commits, path: {2}}) YIELD value "
          + "WITH value.f as f, commits WITH commits, f "
          + "OPTIONAL MATCH (f)-[:RENAMED_FROM*0..]->(f2) WITH collect(f) + collect(f2) as files, commits "
          + "UNWIND commits as c "
          + "UNWIND files as f "
          + "MATCH (c)<-[:CHANGED_IN]-(f) WITH DISTINCT c.authorEmail as email "
          + "MATCH (co:ContributorEntity) WHERE toLower(email) IN co.emails RETURN co")
  List<ContributorEntity> findAllByProjectIdAndFilepathInCommit(
      long projectId, String commitHash, String filepath);

  @Query("MATCH (c:ContributorEntity) RETURN c ORDER BY c.displayName")
  List<ContributorEntity> findAll();
}

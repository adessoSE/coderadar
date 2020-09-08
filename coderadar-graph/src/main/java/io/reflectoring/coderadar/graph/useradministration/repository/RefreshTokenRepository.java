package io.reflectoring.coderadar.graph.useradministration.repository;

import io.reflectoring.coderadar.graph.useradministration.domain.RefreshTokenEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends Neo4jRepository<RefreshTokenEntity, Long> {

  /**
   * @param token The token to look for.
   * @return The RefreshTokenEntity matching the given token.
   */
  @Query("MATCH (r:RefreshTokenEntity) WHERE r.token = $0 RETURN r")
  RefreshTokenEntity findByToken(@NonNull String token);

  /**
   * Deletes a token given a user id.
   *
   * @param userId The user id.
   */
  @Query("MATCH (r:RefreshTokenEntity)<-[:HAS]-(u) WHERE ID(u) = $0 DETACH DELETE r")
  void deleteByUser(long userId);

  /** @param token The token to save. */
  @Query("MATCH (u) WHERE ID(u) = $1 CREATE (u)-[:HAS]->(r:RefreshTokenEntity {token: $0})")
  void saveToken(String token, long userId);
}

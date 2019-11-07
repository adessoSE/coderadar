package io.reflectoring.coderadar.graph.useradministration.repository;

import io.reflectoring.coderadar.graph.useradministration.domain.RefreshTokenEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends Neo4jRepository<RefreshTokenEntity, Long> {

  @Query("MATCH (r:RefreshTokenEntity) WHERE r.token = {0} RETURN r")
  RefreshTokenEntity findByToken(String token);

  @Query("MATCH (r:RefreshTokenEntity)-[:HAS*]-(u:UserEntity) WHERE r.token = {0} RETURN u")
  UserEntity findUserByToken(String token);

  @Query("MATCH (r:RefreshTokenEntity)-[:HAS*]-(u:UserEntity) WHERE ID(u) = {0} DETACH DELETE r")
  void deleteByUser(Long userId);
}

package io.reflectoring.coderadar.graph.projectadministration.user.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.RefreshTokenEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends Neo4jRepository<RefreshTokenEntity, Long> {
  RefreshTokenEntity findByToken(String token);

  @Query("MATCH (r:RefreshTokenEntity)-->(u:UserEntity) WHERE ID(u) = {0} RETURN r")
  RefreshTokenEntity findByUser(Long userId);

  @Query("MATCH (r:RefreshTokenEntity)-->(u:UserEntity) WHERE ID(u) = {0} DETACH DELETE r")
  Long deleteByUser(Long userId);
}

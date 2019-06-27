package io.reflectoring.coderadar.graph.projectadministration.user.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.RefreshTokenEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends Neo4jRepository<RefreshTokenEntity, Long> {
  RefreshTokenEntity findByToken(String token);

  RefreshTokenEntity findByUser(UserEntity user);

  Long deleteByUser(UserEntity user);
}

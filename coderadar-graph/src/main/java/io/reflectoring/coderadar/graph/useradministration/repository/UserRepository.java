package io.reflectoring.coderadar.graph.useradministration.repository;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {

  @Query("MATCH (u:UserEntity) WHERE u.username = {0} RETURN u")
  Optional<UserEntity> findByUsername(String username);
}

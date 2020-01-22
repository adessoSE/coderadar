package io.reflectoring.coderadar.graph.useradministration.repository;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {

  @Query("MATCH (u:UserEntity) WHERE u.username = {0} RETURN u")
  @NonNull
  Optional<UserEntity> findByUsername(@NonNull String username);

  @Query("MATCH (u:UserEntity) WHERE u.username = {0} RETURN COUNT(u) > 0")
  boolean existsByUsername(@NonNull String username);
}

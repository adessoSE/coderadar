package io.reflectoring.coderadar.graph.projectadministration.user.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoadUserRepository extends Neo4jRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(String username);
}

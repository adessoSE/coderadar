package io.reflectoring.coderadar.graph.projectadministration.user.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(String username);
}

package io.reflectoring.coderadar.graph.projectadministration.user.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadUserRepository extends Neo4jRepository<User, Long> {
  Optional<User> findByUsername(String username);
}

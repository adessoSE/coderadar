package io.reflectoring.coderadar.graph.projectadministration.user.repository;

import io.reflectoring.coderadar.projectadministration.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterUserRepository extends Neo4jRepository<User, Long> {}

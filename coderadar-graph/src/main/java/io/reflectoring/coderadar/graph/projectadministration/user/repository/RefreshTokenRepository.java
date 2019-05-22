package io.reflectoring.coderadar.graph.projectadministration.user.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.core.projectadministration.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends Neo4jRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
    RefreshToken findByUser(User user);
    Long deleteByUser(User user);
}

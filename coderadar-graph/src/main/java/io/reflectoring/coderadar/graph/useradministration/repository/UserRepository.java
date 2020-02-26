package io.reflectoring.coderadar.graph.useradministration.repository;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {

  /**
   * @param username The username to search for.
   * @return The UserEntity with the given username or null if nothing is found.
   */
  @Query("MATCH (u:UserEntity) WHERE u.username = {0} RETURN u LIMIT 1")
  UserEntity findByUsername(@NonNull String username);

  /**
   * @param token The refresh token to look for.
   * @return The UserEntity with the given refresh token or null if nothing is found.
   */
  @Query("MATCH (r:RefreshTokenEntity)<-[:HAS]-(u) WHERE r.token = {0} RETURN u LIMIT 1")
  UserEntity findUserByRefreshToken(@NonNull String token);

  /**
   * @param username The username to search for.
   * @return True if a user with the given username exists.
   */
  @Query("MATCH (u:UserEntity) WHERE u.username = {0} RETURN COUNT(u) > 0 LIMIT 1")
  boolean existsByUsername(@NonNull String username);

  /**
   * @param id The user id.
   * @return True if a user with the given id exists.
   */
  @Query("MATCH (u) WHERE ID(u) = {0} RETURN COUNT(u) > 0")
  boolean existsById(@NonNull Long id);
}

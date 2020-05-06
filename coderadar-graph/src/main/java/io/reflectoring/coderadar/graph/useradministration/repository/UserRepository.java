package io.reflectoring.coderadar.graph.useradministration.repository;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import java.util.List;
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
  boolean existsById(long id);

  /**
   * @param userIds The user ids.
   * @return All users matching the given ids.
   */
  @Query("MATCH (u) WHERE ID(u) IN {0} RETURN DISTINCT u")
  List<UserEntity> findAllByIds(List<Long> userIds);

  /**
   * Sets the role of a user for a given project. If the user already has a role in the project, it
   * is deleted and a new one is created in its place.
   *
   * @param projectId The of the project.
   * @param userId The id of the user.
   * @param role The role to set.
   */
  @Query(
      "MATCH (p), (u) WHERE ID(p) = {0} AND ID(u) = {1} WITH p, u "
          + "OPTIONAL MATCH (p)<-[r:ASSIGNED_TO]-(u) DELETE r "
          + "CREATE (p)<-[r:ASSIGNED_TO {role: {2}}]-(u)")
  void setUserRoleForProject(long projectId, long userId, String role);

  /**
   * Deletes the [:ASSIGNED_TO] relationship between a user and a project.
   *
   * @param projectId The project id.
   * @param userId The user id.
   */
  @Query("MATCH (p)<-[r:ASSIGNED_TO]-(u) WHERE ID(p) = {0} AND ID(u) = {1} DELETE r")
  void removeUserRoleFromProject(long projectId, long userId);
}

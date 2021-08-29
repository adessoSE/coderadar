package io.reflectoring.coderadar.graph.useradministration.repository;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {

  /**
   * @param username The username to search for.
   * @return The UserEntity with the given username or null if nothing is found.
   */
  @Query("MATCH (u:UserEntity) WHERE u.username = $0 RETURN u LIMIT 1")
  UserEntity findByUsername(@NonNull String username);

  /**
   * @param token The refresh token to look for.
   * @return The UserEntity with the given refresh token or null if nothing is found.
   */
  @Query("MATCH (r:RefreshTokenEntity)<-[:HAS]-(u) WHERE r.token = $0 RETURN u LIMIT 1")
  UserEntity findUserByRefreshToken(@NonNull String token);

  /**
   * @param username The username to search for.
   * @return True if a user with the given username exists.
   */
  @Query("MATCH (u:UserEntity) WHERE u.username = $0 RETURN COUNT(u) > 0 LIMIT 1")
  boolean existsByUsername(@NonNull String username);

  /**
   * @param id The user id.
   * @return True if a user with the given id exists.
   */
  @Query("MATCH (u:UserEntity) WHERE ID(u) = $0 RETURN COUNT(u) > 0")
  boolean existsById(long id);

  /**
   * @param userIds The user ids.
   * @return All users matching the given ids.
   */
  @Query("MATCH (u) WHERE ID(u) IN $0 RETURN DISTINCT u")
  List<UserEntity> findAllByIds(List<Long> userIds);

  /**
   * Sets the role of a user for a given project. If the user already has a role in the project, it
   * is deleted and a new one is created in its place.
   *
   * @param projectId The of the project.
   * @param userId The id of the user.
   * @param role The role to set.
   * @param creator Set to true if the user is the creator of this project.
   */
  @Query(
      "MATCH (p), (u) WHERE ID(p) = $0 AND ID(u) = $1 WITH p, u "
          + "OPTIONAL MATCH (p)<-[r:ASSIGNED_TO]-(u) DELETE r "
          + "CREATE (p)<-[r1:ASSIGNED_TO {creator: $3, role: $2}]-(u)")
  @Transactional
  void setUserRoleForProject(long projectId, long userId, String role, boolean creator);

  @Query("MATCH (p)<-[r:ASSIGNED_TO]-(u) WHERE ID(p) = $0 AND ID(u) = $1 RETURN r.role LIMIT 1")
  String getUserRoleForProject(long projectId, long userId);

  @Query(
      "MATCH (u)-[:IS_IN]->(t)-[r:ASSIGNED_TO]->(p) WHERE ID(p) = $0 AND ID(u) = $1 RETURN r.role")
  List<String> getUserRolesForProjectInTeams(long projectId, long userId);

  /**
   * Deletes the [:ASSIGNED_TO] relationship between a user and a project.
   *
   * @param projectId The project id.
   * @param userId The user id.
   */
  @Query("MATCH (p)<-[r:ASSIGNED_TO]-(u:UserEntity) WHERE ID(p) = $0 AND ID(u) = $1 DELETE r")
  @Transactional
  void removeUserRoleFromProject(long projectId, long userId);

  @Query(
      "OPTIONAL MATCH (p)<-[:ASSIGNED_TO]-(u:UserEntity) WHERE ID(p) = $0 WITH p, collect(u) as users "
          + "OPTIONAL MATCH (p)<-[:ASSIGNED_TO]-(t:TeamEntity)<-[:IS_IN]-(u) RETURN collect(u) + users as users")
  List<UserEntity> listUsersForProject(long projectId);

  /** @return All users in the database. */
  @Query("MATCH (u:UserEntity) RETURN u ORDER BY toLower(u.username)")
  List<UserEntity> findAll();

  @Query("MATCH (u:UserEntity) WHERE ID(u) = $0 SET u.platformAdmin = $1")
  @Transactional
  void setPlatformPermission(long userId, boolean isAdmin);
}

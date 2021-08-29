package io.reflectoring.coderadar.graph.useradministration.repository;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TeamRepository extends Neo4jRepository<TeamEntity, Long> {

  /**
   * @param userId The user id.
   * @return All teams the user is in.
   */
  @Query(
      "MATCH (u)-[:IS_IN]->(t) WHERE ID(u) = $0 WITH t "
          + "OPTIONAL MATCH (t)<-[r:IS_IN]-(u) RETURN t, r, u ORDER BY toLower(t.name)")
  List<TeamEntity> listTeamsByUserId(long userId);

  /**
   * Creates a [:HAS_TEAM] relationship between a project and a team.
   *
   * @param projectId The id of the project.
   * @param teamId The id of the team.
   * @param role The role of the team in the project.
   */
  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = $0 "
          + "MATCH (t) WHERE ID(t) = $1 WITH p, t "
          + "OPTIONAL MATCH (t)-[r:ASSIGNED_TO]->(p) DELETE r "
          + "CREATE (t)-[r1:ASSIGNED_TO {role: $2}]->(p)")
  @Transactional
  void addTeamToProject(long projectId, long teamId, String role);

  @Query(
      "MATCH (t) WHERE ID(t) = $0 WITH t "
          + "UNWIND $1 as x "
          + "MATCH (u) WHERE ID(u) = x "
          + "MERGE (u)-[r:IS_IN]->(t)")
  @Transactional
  void addUsersToTeam(long teamId, List<Long> userIds);

  @Query(
      "MATCH (t) WHERE ID(t) = $0 WITH t "
          + "UNWIND $1 as x "
          + "MATCH (u)-[r:IS_IN]->(t) WHERE ID(u) = x "
          + "DELETE r")
  @Transactional
  void deleteUsersFromTeam(long teamId, List<Long> userIds);

  /**
   * @param teamId The team id.
   * @return The team along with all of its members;
   */
  @Query("MATCH (u)-[r:IS_IN*0..1]->(t) WHERE ID(t) = $0 RETURN t, r, u")
  TeamEntity findByIdWithMembers(long teamId);

  @Query("MATCH (t:TeamEntity) WHERE ID(t) = $0 RETURN COUNT(t) > 0")
  boolean existsById(long teamId);

  @Query("MATCH (t:TeamEntity) WHERE t.name = $0 RETURN COUNT(t) > 0")
  boolean existsByName(String name);

  /**
   * @param projectId The project id.
   * @return All teams assigned to the project along with their members.
   */
  @Query(
      "MATCH (p)<-[:ASSIGNED_TO]-(t)<-[r:IS_IN*0..1]-(u) WHERE ID(p) = $0 RETURN t, r, u ORDER BY toLower(t.name)")
  List<TeamEntity> listTeamsByProjectIdWithMembers(long projectId);

  /**
   * Deletes the [:ASSIGNED_TO] relationship between the team and the project.
   *
   * @param projectId The id of the project.
   * @param teamId The id of the team.
   */
  @Query("MATCH (p)<-[r:ASSIGNED_TO]-(t) WHERE ID(p) = $0 AND ID(t) = $1 DELETE r")
  @Transactional
  void removeTeamFromProject(long projectId, long teamId);

  /** @return All teams in the database along with their members. */
  @Query("MATCH (t:TeamEntity)<-[r:IS_IN*0..1]-(u) RETURN t, r, u ORDER BY toLower(t.name)")
  List<TeamEntity> findAllWithMembers();

  @Query("MATCH (t:TeamEntity) WHERE t.name = $0 RETURN t")
  TeamEntity findByName(String name);

  @Query(
      "MATCH (t:TeamEntity)<-[:IS_IN]-(u:UserEntity) WHERE ID(u) = $0 "
          + "AND SIZE ((t)<-[:IS_IN]-()) = 1 DETACH DELETE t")
  @Transactional
  void deleteIfOnlyUserWithIdRemains(long userId);
}

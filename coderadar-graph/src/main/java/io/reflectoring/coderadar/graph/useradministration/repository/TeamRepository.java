package io.reflectoring.coderadar.graph.useradministration.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TeamRepository extends Neo4jRepository<TeamEntity, Long> {

  /**
   * @param userId The user id.
   * @return All teams the user is in.
   */
  @Query("MATCH (u)-[:IS_IN]->(t) WHERE ID(u) = {0} RETURN t")
  List<TeamEntity> listTeamsByUserId(long userId);

  /**
   * Creates a [:HAS_TEAM] relationship between a project and a team.
   *
   * @param projectId The id of the project.
   * @param teamId The id of the team.
   * @param role The role of the team in the project.
   */
  @Query(
      "MATCH (p) WHERE ID(p) = {0} "
          + "MATCH (t) WHERE ID(t) = {1} WITH p, t "
          + "CREATE (t)-[r:ASSIGNED_TO {role: {2}}]->(p)")
  void addTeamToProject(long projectId, long teamId, String role);
}

package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends Neo4jRepository<ProjectEntity, Long> {

  Optional<ProjectEntity> findByName(String name);

  List<ProjectEntity> findAllByName(String name);

  /*  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = {projectId} " +
              "OPTIONAL MATCH (p)-->(ac:AnalyzerConfigurationEntity) " +
              "OPTIONAL MATCH (p)-->(aj:AnalyzingJobEntity) " +
              "OPTIONAL MATCH (p)-->(fp:FilePatternEntity) " +
              "OPTIONAL MATCH (p)-->(c:CommitEntity) " +
              "OPTIONAL MATCH (c)<--(f:FileEntity) " +
              "OPTIONAL MATCH (f)<--(m:ModuleEntity) " +
              "OPTIONAL MATCH (c)<--(mv:MetricValueEntity) " +
              "OPTIONAL MATCH (mv)-->(fi:FindingEntity) " +
              "DETACH DELETE m, fi, mv, f, c, ac, aj, fp, p ")
  void deleteProjectCascade(@Param("projectId") Long projectId);*/

  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = {projectId} "
          + "OPTIONAL MATCH (p)-->()<--(mv:MetricValueEntity) "
          + "OPTIONAL MATCH (mv)-->(fi:FindingEntity) "
          + "DETACH DELETE fi ")
  void deleteProjectFindings(@Param("projectId") Long projectId);

  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = {projectId} "
          + "OPTIONAL MATCH (p)-->()<--(mv:MetricValueEntity) "
          + "DETACH DELETE mv ")
  void deleteProjectMetrics(@Param("projectId") Long projectId);

  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = {projectId} "
          + "OPTIONAL MATCH (p)-->()<--(f:FileEntity)<--(m:ModuleEntity) "
          + "DETACH DELETE m, f")
  void deleteProjectFilesAndModules(@Param("projectId") Long projectId);

  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = {projectId} "
          + "OPTIONAL MATCH (p)-->(c) "
          + "DETACH DELETE c ")
  void deleteProjectCommits(@Param("projectId") Long projectId);

  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = {projectId} "
          + "OPTIONAL MATCH (p)-->(ac:AnalyzerConfigurationEntity) "
          + "OPTIONAL MATCH (p)-->(aj:AnalyzingJobEntity) "
          + "OPTIONAL MATCH (p)-->(fp:FilePatternEntity) "
          + "DETACH DELETE ac, aj, fp")
  void deleteProjectConfiguration(@Param("projectId") Long projectId);
}

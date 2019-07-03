package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateModuleRepository extends Neo4jRepository<ModuleEntity, Long> {

    @Query("MATCH (p:ProjectEntity)-[r:CONTAINS]->(f:FileEntity) WHERE ID(p) = {projectId} AND ID(f) = {fileId} DELETE r")
    void detachFileFromProject(@Param("projectId") Long projectId, @Param("fileId") Long fileId);

    @Query("MATCH (p:ModuleEntity)-[r:CONTAINS]->(f:FileEntity) WHERE ID(p) = {moduleId} AND ID(f) = {fileId} DELETE r")
    void detachFileFromModule(@Param("moduleId") Long moduleId, @Param("fileId") Long fileId);
}

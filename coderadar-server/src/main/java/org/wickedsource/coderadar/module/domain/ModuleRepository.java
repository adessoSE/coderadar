package org.wickedsource.coderadar.module.domain;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wickedsource.coderadar.commit.domain.CommitToFileAssociation;

public interface ModuleRepository extends CrudRepository<Module, Long> {

  long countByProjectIdAndPath(Long projectId, String path);

  Module findByIdAndProjectId(Long id, Long projectId);

  Page<Module> findByProjectId(Long projectId, Pageable pageable);

  @Query(
      "select a from CommitToFileAssociation a where a.id.file.filepath LIKE CONCAT(:modulePath,'/%') order by a.id.file.filepath")
  List<CommitToFileAssociation> findFilesByModulePath(@Param("modulePath") String modulePath);

  @Query("select a from CommitToFileAssociation a join a.modules m where m.id = :moduleId")
  List<CommitToFileAssociation> findFilesByModuleId(@Param("moduleId") Long moduleId);

  /** Lists all Modules that are a parent path of the specified file path. */
  @Query(
      "select m from Module m where :filepath LIKE CONCAT(m.path, '/%') order by m.path")
  List<Module> findModulesForFile(@Param("filepath") String filepath);

  int deleteByProjectId(Long id);
}

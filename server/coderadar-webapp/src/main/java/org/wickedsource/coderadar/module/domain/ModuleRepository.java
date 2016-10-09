package org.wickedsource.coderadar.module.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ModuleRepository extends CrudRepository<Module, Long> {

    long countByProjectIdAndPath(Long projectId, String path);

    Module findByIdAndProjectId(Long id, Long projectId);

    Page<Module> findByProjectId(Long projectId, Pageable pageable);

}

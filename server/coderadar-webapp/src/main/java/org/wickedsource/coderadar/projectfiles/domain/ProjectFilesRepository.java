package org.wickedsource.coderadar.projectfiles.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectFilesRepository extends CrudRepository<ProjectFiles, Long> {

    List<ProjectFiles> findByProjectId(Long projectId);

    void deleteByProjectId(Long projectId);
}

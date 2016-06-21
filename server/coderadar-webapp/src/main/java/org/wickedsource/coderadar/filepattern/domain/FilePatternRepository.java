package org.wickedsource.coderadar.filepattern.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilePatternRepository extends CrudRepository<FilePattern, Long> {

    List<FilePattern> findByProjectId(Long projectId);

    List<FilePattern> findByProjectIdAndFileSet(Long projectId, FileSet fileSet);

    void deleteByProjectId(Long projectId);
}

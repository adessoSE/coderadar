package org.wickedsource.coderadar.filepattern.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilePatternRepository extends CrudRepository<FilePattern, Long> {

    List<FilePattern> findByProjectId(Long projectId);

    List<FilePattern> findByProjectIdAndFileSetType(Long projectId, FileSetType fileSetType);

    int deleteByProjectId(Long projectId);
}

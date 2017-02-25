package org.wickedsource.coderadar.filepattern.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface FilePatternRepository extends CrudRepository<FilePattern, Long> {

  List<FilePattern> findByProjectId(Long projectId);

  List<FilePattern> findByProjectIdAndFileSetType(Long projectId, FileSetType fileSetType);

  int deleteByProjectId(Long projectId);
}

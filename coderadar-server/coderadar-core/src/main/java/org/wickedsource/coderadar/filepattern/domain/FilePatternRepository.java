package org.wickedsource.coderadar.filepattern.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.projectadministration.domain.FilePattern;
import org.wickedsource.coderadar.projectadministration.domain.FileSetType;

public interface FilePatternRepository extends CrudRepository<FilePattern, Long> {

  List<FilePattern> findByProjectId(Long projectId);

  List<FilePattern> findByProjectIdAndFileSetType(Long projectId, FileSetType fileSetType);

  int deleteByProjectId(Long projectId);

  int countByProjectId(Long projectId);
}

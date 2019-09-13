package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;

public interface DeleteFilePatternFromProjectUseCase {
  void delete(Long filePatternId, Long projectId) throws FilePatternNotFoundException;
}

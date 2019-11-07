package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete;

public interface DeleteFilePatternFromProjectUseCase {
  void delete(Long filePatternId, Long projectId);
}

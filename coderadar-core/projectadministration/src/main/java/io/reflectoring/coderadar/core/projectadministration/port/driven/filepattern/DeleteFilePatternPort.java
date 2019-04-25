package io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;

public interface DeleteFilePatternPort {
  void delete(Long id);

  void delete(FilePattern filePattern);
}

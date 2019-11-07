package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;

public interface DeleteFilePatternPort {
  void delete(Long id);

  void delete(FilePattern filePattern);
}

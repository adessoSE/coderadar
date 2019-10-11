package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;

public interface DeleteFilePatternPort {
  void delete(Long id) throws FilePatternNotFoundException;

  void delete(FilePattern filePattern) throws FilePatternNotFoundException;
}

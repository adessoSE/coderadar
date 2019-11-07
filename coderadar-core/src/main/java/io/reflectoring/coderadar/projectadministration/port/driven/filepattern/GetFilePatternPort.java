package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;

public interface GetFilePatternPort {
  FilePattern get(Long id);
}

package io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;

public interface UpdateFilePatternPort {
  void updateFilePattern(FilePattern filePattern);
}

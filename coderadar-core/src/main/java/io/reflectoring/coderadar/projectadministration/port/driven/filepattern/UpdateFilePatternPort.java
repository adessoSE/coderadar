package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;

public interface UpdateFilePatternPort {
  void updateFilePattern(FilePattern filePattern) throws FilePatternNotFoundException;
}
